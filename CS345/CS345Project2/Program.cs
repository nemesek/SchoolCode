using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Collections;


namespace CS345Project2
{
    class Program
    {
        const int DICTIONARY = 77952;
        //const int DICTIONARY = 25;
        const int COLLECTION = 19976;
        static int[] categoryNTs = new int[20];
        static Dictionary<String, decimal>[] termCategoryEstimates = new Dictionary<String, decimal>[20]; //Array of Dictionaries with term name as key and Pr(term | category) for particular category as value
        static Dictionary<String, decimal[]> njtBayesDictionary = new Dictionary<string, decimal[]>(); //Dictionary storing all terms as keys and  their Pr(term|categories) for all categories as arrays
        static int[] documentCategories = new int[COLLECTION]; //Store the arg max category foreach document as computed by category membership algo (FindMaxCategory())
        static int[] assignmedDocumentCategories = new int[COLLECTION];
        static int[][] confusionMatrix = new int[20][];
  
        static void Main(string[] args)
        {
            Console.Write(DateTime.Now.Date.ToString() + " " );
            Console.WriteLine(DateTime.Now.TimeOfDay.ToString());
            Dictionary<String, ArrayList> documents = new Dictionary<string,ArrayList>();
            ArrayList[] categories = new ArrayList[20]; //list of documents foreach category
            decimal[] categoryPR = new decimal[20]; //Pr(category) array
            int[][] termNJTs = new int[DICTIONARY][]; //category tf values foreach term
            //Dictionary<String, decimal[]> njtBayesDictionary = new Dictionary<string, decimal[]>();
            
            InitializeTermCategoryDictionaries();
            InitializeConfusionMatrix();
            BuildCategoryVectors(categories); //Stores a list of documents foreach category
            CalculateCategoryProbabilities(categoryPR, categories); //Stores Pr(category) into an array
            BuildDocumentVectors(documents); //stores the terms and tfs foreach term in every doc
            Console.WriteLine("Finished BuildDocumentVectors()");
            BuildNJTArray(termNJTs, categories, documents); //jagged array -- stores an array of term frequencies foreach category foreach term in the DICTIONARY
            Console.WriteLine("Finished BuildNJTArray()");

            for (int i = 0; i < DICTIONARY; i++)
            {
                int termNum = i + 1;
                String term = termNum.ToString();
                int[] tempNJT = termNJTs[i];    //int[] tempNJT = CalculateNJT(term, categories, documents);
                decimal[] tempNjtBayes = new decimal[20];
                ComputeBayesianEstimates(tempNJT, tempNjtBayes); //stores array of Pr(Term|Category) probabilities foreach category for a term
                njtBayesDictionary.Add(term, tempNjtBayes);
                UpdateTermCategoryEstimates(tempNjtBayes, term); //stores term Pr(Term|Category) into category Dictionary
            }
            Console.WriteLine("Finished UpdateTermCategoryEstimates()");
            //Print out top5 terms for each category
            for (int i = 0; i < 20; i++)
            {
                string file = @"C:\Users\Socrates\Documents\Visual Studio 2010\Projects\CS345Project2\CS345Project2\output.txt";
                int cat = i+1;
                //Console.WriteLine("Category " + cat.ToString() + " top 20");
                var items = (from k in termCategoryEstimates[i].Keys
                             orderby termCategoryEstimates[i][k] descending
                             select k).Take(20);
                using (StreamWriter writer = new StreamWriter(file, true))
                {
                    writer.WriteLine("Category " + cat.ToString() + " top 20");
                    foreach (String k in items)
                    {
                        
                        //Console.WriteLine("{0}: {1}", k, termCategoryEstimates[i][k]);
                        writer.WriteLine("{0} : {1}", k, termCategoryEstimates[i][k]);

                    }

                }


            }
            DocumentCategoryEstimator(documents, categoryPR);
            Console.WriteLine("Done with DocumentCategoryEstimator()");
            decimal pAve = AveragePrecision();
            Console.WriteLine("Average Precision: " + pAve.ToString());
            Console.WriteLine("Done");
            Console.Write(DateTime.Now.Date.ToString() + " ");
            Console.WriteLine(DateTime.Now.TimeOfDay.ToString());

        }
        static private void InitializeConfusionMatrix()
        {
            for (int i = 0; i < 20; i++)
            {
                confusionMatrix[i] = new int[20];
            }
        }
        static private void DocumentCategoryEstimator(Dictionary<String, ArrayList> documents, decimal[] categoryPR)
        {
            for (int i = 0; i < COLLECTION; i++)
            {
                ArrayList docTermArray;
                int docNum = i + 1;
                String documentID = docNum.ToString();
                documents.TryGetValue(documentID, out docTermArray);
                if (docTermArray != null)
                {
                    int max = FindMaxCategory(docTermArray, categoryPR);
                    documentCategories[i] = max-1;
                    //update confusion matrix
                    int row = assignmedDocumentCategories[i];
                    int column = documentCategories[i];
                    confusionMatrix[row][column] += 1;
                    int x = row + column; //TODO - remove just using for bp purposes                   

                }
                else
                    documentCategories[i] = -1;         //TODO - investigate  if this condition occurs when doing a full run

            }

        }
        static private decimal AveragePrecision()
        {
            decimal pAve = 0;
            
            for (int i = 0; i < 20; i++)
            {
                decimal numerator = 0;
                numerator += confusionMatrix[i][i];
                decimal denominator = 0;
                for (int j = 0; j < 20; j++)
                {
                    denominator += confusionMatrix[i][j];   
                }
                pAve += (numerator / denominator);
            }
            pAve /= 20;
            return pAve;
        }
        static private int FindMaxCategory(ArrayList document, decimal[] categoryPR)
        {
            decimal maxTotal = 0;
            int maxCategory = 0;
            for (int i = 0; i < 20; i++)
            {
                decimal total = 0;
                decimal prior = categoryPR[i];
                for (int j = 0; j < document.Count; j++)
                {
                    String term = document[j].ToString();
                    String[] split = term.Split(' ');
                    decimal termFrequency = Convert.ToDecimal(split[1]);
                    decimal[] termBayesArray;
                    njtBayesDictionary.TryGetValue(split[0], out termBayesArray);
                    if(termBayesArray != null)
                        total += (termFrequency * termBayesArray[i]);

                }
                total += prior;
                if (total > maxTotal)
                {
                    maxTotal = total;
                    maxCategory = i + 1;
                }
            }
            return maxCategory;
        }
        static private void InitializeTermCategoryDictionaries()
        {
            for (int i = 0; i < 20; i++)
            {
                termCategoryEstimates[i] = new Dictionary<String, decimal>();
            }
        }

        static private void UpdateTermCategoryEstimates(decimal[] njtBayes, String term)
        {
            for (int i = 0; i < 20; i++)
            {
                termCategoryEstimates[i].Add(term, njtBayes[i]);
            }            
        }

        static private void ComputeBayesianEstimates(int[] njt, decimal[] njtBayes)
        {
            decimal result;
            for (int i = 0; i < 20; i++)
            {                
                decimal numerator = (decimal)(njt[i] + 1);
                decimal denominator = (decimal)(categoryNTs[i] + DICTIONARY);
                result = numerator / denominator;
                njtBayes[i] = result;
            }

        }

        static private void BuildNJTArray(int[][] termNJTs, ArrayList[] categories, Dictionary<String, ArrayList> documents)
        {
            for (int i = 0; i < DICTIONARY; i++)
            {
                String term = Convert.ToString(i + 1);
                int[] njt = CalculateNJT(term, categories, documents); //Stores term frequency (njt) foreach category foreach term
                termNJTs[i] = njt;               
               
            }
        }


        static private int[] CalculateNJT(String term, ArrayList[] categories, Dictionary<String, ArrayList> documents)
        {
            int[] njt = new int[20];
            for (int i = 0; i < categories.Length; i++)
            {
                object[] tempArray = categories[i].ToArray();
                for (int j = 0; j < tempArray.Length; j++)
                {
                    String key = tempArray[j].ToString();
                    ArrayList temp;
                    if (documents.ContainsKey(key))
                    {
                        int category = i + 1;
                        documents.TryGetValue(key, out temp);
                        for (int k = 0; k < temp.Count; k++)
                        {
                            String element = temp[k].ToString();
                            String[] split = element.Split(' ');
                            if(String.Compare(split[0], term) == 0)
                            {
                                int frequency = Convert.ToInt32(split[1]);
                                njt[i] += frequency;
                                categoryNTs[i] += frequency;
                                
                            }
                        }
                    }
                }
            }
            return njt;
        }

        static private void CalculateCategoryProbabilities(decimal[] categoryPR, ArrayList[] categories)
        {
            for (int i = 0; i < categoryPR.Length; i++)
            {
                decimal numerator = categories[i].Count;
                categoryPR[i] = numerator / COLLECTION;
                //Console.WriteLine(categoryPR[i].ToString());
            }
        }

        static private void BuildDocumentVectors(Dictionary<String, ArrayList> documents)
        {

            try
            {
                using (StreamReader sr = new StreamReader("C:\\Users\\Socrates\\Desktop\\IBack\\School\\CS 345\\Project2\\csci345_pj2\\wordvector")) 
                {
                    String strLine;
   
                    while ((strLine = sr.ReadLine()) != null)
                    {
                        String[] split = strLine.Split(' ');
                        ArrayList temp;
                        if (documents.TryGetValue(split[1], out temp))
                        {
                            temp.Add(split[0] + " " + split[2]);
                        }
                        else
                        {
                            ArrayList docList = new ArrayList();
                            docList.Add(split[0] + " " + split[2]);
                            documents.Add(split[1], docList);
                        }

                    }

                }

                    
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace.ToString());
            }

        }

        static private void BuildCategoryVectors(ArrayList[] categories)
        {
            try
            {
                using (StreamReader sr = new StreamReader("C:\\Users\\Socrates\\Desktop\\IBack\\School\\CS 345\\Project2\\csci345_pj2\\category"))
                {
                    String strLine;
                    int catNum = 0;
                    while ((strLine = sr.ReadLine()) != null)
                    {
                        //Console.WriteLine(strLine);
                        String[] split = strLine.Split(' ');
                        //Console.WriteLine(split[0]);
                        int temp = Convert.ToInt32(split[1]);
                        int docNum = Convert.ToInt32(split[0]);
                        docNum--;
                        
                        
                        if (temp > catNum)
                        {
                            //LinkedList<int> catList = new LinkedList<int>();
                            assignmedDocumentCategories[docNum] = catNum;
                            ArrayList catList = new ArrayList();
                            categories[catNum] = catList;
                            catList.Add(split[0]);
                            catNum = temp;
                        }
                        else
                        {
                            assignmedDocumentCategories[docNum] = catNum-1;
                            categories[catNum - 1].Add(split[0]);
                        }

                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace.ToString());
            }
        }

    }
}
