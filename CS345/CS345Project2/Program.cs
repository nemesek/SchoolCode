using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.IO;

namespace DictionaryScratch
{
    class Program
    {
        const int DICTIONARY = 77952;
        const int COLLECTION = 19976;
        static Dictionary<String, ArrayList> documents = new Dictionary<string, ArrayList>();
        static Dictionary<String, ArrayList> docTerms = new Dictionary<string, ArrayList>();
        static Dictionary<String, String> vocabTerms = new Dictionary<string, string>();
        static ArrayList[] categories = new ArrayList[20]; //list of documents foreach category
        static Dictionary<String, int[]> categoryTermFrequencies = new Dictionary<string, int[]>(); //termfrequency foreach term foreach term
        static int[] assignedDocumentCategories = new int[COLLECTION];
        static Dictionary<String, decimal>[] termCategoryEstimates = new Dictionary<String, decimal>[20]; //Array of Dictionaries with term name as key and Pr(term | category) for particular category as value
        static Dictionary<String, decimal>[] termTdfIdfCategoryEstimates = new Dictionary<String, decimal>[20]; //Stores the tdfidf foreach category foreach term
        static int[][] tdfIdfConfusionMatrix = new int[20][];
        static int[][] confusionMatrix = new int[20][];
        static decimal[] categoryPR = new decimal[20]; //Pr(category) array
        static int[] categoryNTs = new int[20];
        static Dictionary<String, decimal[]> njtBayesDictionary = new Dictionary<string, decimal[]>(); //Dictionary storing all terms as keys and  their Pr(term|categories) for all categories as arrays
        static int[] documentCategories = new int[COLLECTION]; //Store the arg max category foreach document as computed by category membership algo (FindMaxCategory())
        //Part2 Specific structures
        static Dictionary<String, decimal[]> docTdfIdfVector = new Dictionary<String, decimal[]>();
        //static decimal[] totalTF = new decimal[DICTIONARY]; //Store all TF's
        static Dictionary<String, decimal[]> termTdfIdfForEachCategory = new Dictionary<String, decimal[]>();
        static Dictionary<String, decimal[]> tdfIdfBayes = new Dictionary<string, decimal[]>(); //Store the tdf-idf Pr(term| category)
        static decimal[] termDocumentCount = new decimal[DICTIONARY];
        static int[] tdfIdfDocumentCategories = new int[COLLECTION];

        static void Main(string[] args)
        {
            Console.Write(DateTime.Now.Date.ToString() + " ");
            Console.WriteLine(DateTime.Now.TimeOfDay.ToString());
            BuildTermDictionary();
            InitializeTermCategoryDictionaries();
            InitializeConfusionMatrix();
            BuildCategoryVectors(); //Stores a list of documents foreach category
            CalculateCategoryProbabilities();
            BuildDocumentVectors(); //Stores the terms and tfs foreach term in every doc and also builds the termcategoryfrequency foreach term

            for (int i = 0; i < DICTIONARY; i++)
            {
                int termNum = i + 1;
                String term = termNum.ToString();
                int[] tempNJT;
                categoryTermFrequencies.TryGetValue(term, out tempNJT);
                if (tempNJT != null)
                {
                    decimal[] tempNjtBayes = new decimal[20];
                    ComputeBayesianEstimates(tempNJT, tempNjtBayes); //stores array of Pr(Term|Category) probabilities foreach category for a term
                    njtBayesDictionary.Add(term, tempNjtBayes);
                    UpdateTermCategoryEstimates(tempNjtBayes, term); //stores term Pr(Term|Category) into category Dictionary

                }

            }
            DisplayTop20(termCategoryEstimates, 1);
            DocumentCategoryEstimator();
            decimal pAve = AveragePrecision(confusionMatrix);
            Console.WriteLine("Average Precision: " + pAve.ToString());
            //Part2
            BuildTdfIdfVector();
            for (int i = 0; i < DICTIONARY; i++)
            {
                int termID = i + 1;
                String term = termID.ToString();
                decimal[] termIDFForCategory;
                termTdfIdfForEachCategory.TryGetValue(term, out termIDFForCategory);
                if (termIDFForCategory != null)
                {
                    ComputeTdfIdfBayesianEstimates(term, termIDFForCategory);
                    UpdateTermTdfIdfCategoryEstimates(term, termIDFForCategory);
                }
            }
            DisplayTop20(termTdfIdfCategoryEstimates, 2);
            TdfIdfDocumentCategoryEstimator();
            decimal pAve2 = AveragePrecision(tdfIdfConfusionMatrix);
            Console.WriteLine("Average Precision2: " + pAve2.ToString());
            Console.WriteLine("Done");
            Console.Write(DateTime.Now.Date.ToString() + " ");
            Console.WriteLine(DateTime.Now.TimeOfDay.ToString());
        }
        static void BuildTermDictionary()
        {
            try
            {
                using (StreamReader sr = new StreamReader(@"C:\Users\Socrates\Desktop\IBack\School\CS 345\Project2\csci345_pj2\vocabulary"))
                {
                    String strLine;
                    int lineNum = 1;
                    while ((strLine = sr.ReadLine()) != null)
                    {
                        //String[] split = strLine.Split(' ');
                        vocabTerms.Add(lineNum.ToString(), strLine);
                        lineNum++;
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace.ToString());
            }
        }
        static void BuildCategoryVectors()
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
                            assignedDocumentCategories[docNum] = catNum;
                            ArrayList catList = new ArrayList();
                            categories[catNum] = catList;
                            catList.Add(split[0]);
                            catNum = temp;
                        }
                        else
                        {
                            assignedDocumentCategories[docNum] = catNum-1;
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
        static private void BuildDocumentVectors()
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
                        ArrayList docTermsTemp;
                        if (docTerms.TryGetValue(split[1], out docTermsTemp))
                        {
                            docTermsTemp.Add(split[0]);
                        }
                        else
                        {
                            docTermsTemp = new ArrayList();
                            docTermsTemp.Add(split[0]);
                            docTerms.Add(split[1], docTermsTemp);
                        }
                        int[] catTermFrequencyArray;
                        if (documents.TryGetValue(split[1], out temp))
                        {
                            temp.Add(split[0] + " " + split[2]);
                            categoryTermFrequencies.TryGetValue(split[0], out catTermFrequencyArray);
                            if (catTermFrequencyArray != null)
                            {
                                int termFrequency = Convert.ToInt32(split[2]);
                                int docID = Convert.ToInt32(split[1]);
                                docID--;
                                int categoryID = assignedDocumentCategories[docID];
                                catTermFrequencyArray[categoryID] += termFrequency;
                                int termID = Convert.ToInt32(split[0]);
                                termID--;
                                //totalTF[termID] += termFrequency;
                                categoryNTs[categoryID] += termFrequency;
                                termDocumentCount[termID] += 1;

                            }
                            else
                            {
                                
                                catTermFrequencyArray = new int[20];
                                int termFrequency = Convert.ToInt32(split[2]);
                                int docID = Convert.ToInt32(split[1]);
                                docID--;
                                int categoryID = assignedDocumentCategories[docID];
                                catTermFrequencyArray[categoryID] += termFrequency;
                                categoryTermFrequencies.Add(split[0], catTermFrequencyArray);
                                int termID = Convert.ToInt32(split[0]);
                                termID--;
                                //totalTF[termID] += termFrequency;
                                categoryNTs[categoryID] += termFrequency;
                                termDocumentCount[termID] += 1;

                            }
                        }
                        else
                        {
                            
                            ArrayList docList = new ArrayList();
                            docList.Add(split[0] + " " + split[2]);
                            documents.Add(split[1], docList);
                            categoryTermFrequencies.TryGetValue(split[0], out catTermFrequencyArray);
                            if (catTermFrequencyArray != null)
                            {
                                int termFrequency = Convert.ToInt32(split[2]);
                                int docID = Convert.ToInt32(split[1]);
                                docID--;
                                int categoryID = assignedDocumentCategories[docID];
                                catTermFrequencyArray[categoryID] += termFrequency;
                                int termID = Convert.ToInt32(split[0]);
                                termID--;
                                //totalTF[termID] += termFrequency;
                                categoryNTs[categoryID] += termFrequency;
                                termDocumentCount[termID] += 1;
                            }
                            else
                            {
                                catTermFrequencyArray = new int[20];
                                int termFrequency = Convert.ToInt32(split[2]);
                                int docID = Convert.ToInt32(split[1]);
                                docID--;
                                int categoryID = assignedDocumentCategories[docID];
                                catTermFrequencyArray[categoryID] += termFrequency;
                                categoryTermFrequencies.Add(split[0], catTermFrequencyArray);
                                int termID = Convert.ToInt32(split[0]);
                                termID--;
                                //totalTF[termID] += termFrequency;
                                categoryNTs[categoryID] += termFrequency;
                                termDocumentCount[termID] += 1;
                            }
                        }

                    }

                }


            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace.ToString());
            }

        }
        static private void InitializeTermCategoryDictionaries()
        {
            for (int i = 0; i < 20; i++)
            {
                termCategoryEstimates[i] = new Dictionary<String, decimal>();
                termTdfIdfCategoryEstimates[i] = new Dictionary<String, decimal>();
            }
        }
        static private void InitializeConfusionMatrix()
        {
            for (int i = 0; i < 20; i++)
            {
                confusionMatrix[i] = new int[20];
                tdfIdfConfusionMatrix[i] = new int[20];
            }
        }
        static private void CalculateCategoryProbabilities()
        {
            for (int i = 0; i < categoryPR.Length; i++)
            {
                decimal numerator = categories[i].Count;
                categoryPR[i] = numerator / COLLECTION;
                //Console.WriteLine(categoryPR[i].ToString());
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
        static private void UpdateTermCategoryEstimates(decimal[] njtBayes, String term)
        {
            for (int i = 0; i < 20; i++)
            {
                termCategoryEstimates[i].Add(term, njtBayes[i]);
            }
        }
        static private void DisplayTop20( Dictionary<String, decimal>[]tce, int num)
        {
            string file;
            if (num == 1)
            {
                 file = @"C:\Users\Socrates\Documents\Visual Studio 2010\Projects\DictionaryScratch\output.txt";
            }
            else
                file = @"C:\Users\Socrates\Documents\Visual Studio 2010\Projects\DictionaryScratch\output2.txt";
            for (int i = 0; i < 20; i++)
            {
               
                int cat = i + 1;

                var items = (from k in tce[i].Keys
                             orderby tce[i][k] descending
                             select k).Take(20);
                using (StreamWriter writer = new StreamWriter(file, true))
                {
                    writer.WriteLine("Category " + cat.ToString() + " top 20");
                    foreach (String k in items)
                    {
                        String term;
                        vocabTerms.TryGetValue(k, out term);                        
                        writer.WriteLine("{0} : {1} : {2}", k,term,  tce[i][k]);

                    }

                }


            }
        }
        static private void DocumentCategoryEstimator()
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
                    documentCategories[i] = max - 1;
                    //update confusion matrix
                    int row = assignedDocumentCategories[i];
                    int column = documentCategories[i];
                    confusionMatrix[row][column] += 1;
                    int x = row + column; //TODO - remove just using for bp purposes                   

                }
                else
                    documentCategories[i] = -1;         //TODO - investigate  if this condition occurs when doing a full run


            }
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
                    if (termBayesArray != null)
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
        static private decimal AveragePrecision(int[][] confusionMatrix)
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
        //Part 2 specific methods
        static private void BuildTdfIdfVector()
        {

            for (int i = 0; i < documents.Count; i++)
            {
                int num = i + 1;
                String docNum = num.ToString();
                ArrayList docTerms;
                documents.TryGetValue(docNum, out docTerms);
                if (docTerms != null)
                {
                    decimal[] docTfArray = new decimal[docTerms.Count];
                    for (int j = 0; j < docTerms.Count; j++)
                    {
                        //Console.WriteLine(docTerms[j].ToString());
                        String entry = docTerms[j].ToString();
                        String[] split = entry.Split(' ');
                        int termID = Convert.ToInt32(split[0]);
                        int frequency = Convert.ToInt32(split[1]);
                        termID--;
                        if (termID < DICTIONARY)
                        {
                            //decimal totalTermFrequency = totalTF[termID];
                            decimal totalTermFrequency = termDocumentCount[termID];
                            double argument = (double)COLLECTION / (double)totalTermFrequency;
                            decimal idf = (decimal)Math.Log(argument);
                            decimal tdfIDF = frequency * idf;
                            docTfArray[j] = tdfIDF;
                            //Added
                            int docID = Convert.ToInt32(docNum);
                            docID--;
                            int category = assignedDocumentCategories[docID];
                            decimal[] tempTermCategoryTdfIdf;
                            termTdfIdfForEachCategory.TryGetValue(split[0], out tempTermCategoryTdfIdf);
                            if (tempTermCategoryTdfIdf != null)
                            {
                                tempTermCategoryTdfIdf[category] += tdfIDF;
                            }
                            else
                            {
                                tempTermCategoryTdfIdf = new decimal[20];
                                termTdfIdfForEachCategory.Add(split[0], tempTermCategoryTdfIdf);
                            }
   
                            
                        }


                    }
                    docTdfIdfVector.Add(docNum, docTfArray); //tdf-idf foreach term for every document

                }


            }
        }        
        static private void ComputeTdfIdfBayesianEstimates(String term, decimal[] termIDFForCategory)
        {
            decimal[] resultArray = new decimal[20];
            decimal result;
            
            //for (int i = 0; i < termDocumentCount.Length; i++)
            for (int i = 0; i < termIDFForCategory.Length; i++)
            {
                decimal numerator = (decimal)termIDFForCategory[i] + 1;
                //decimal numerator = termDocumentCount[i] + 1;
                decimal denominator = (decimal)categoryNTs[i] + DICTIONARY;
                //decimal denominator = (decimal)categoryNTs[i];
                result = numerator / denominator;
                resultArray[i] = result;
            }
            tdfIdfBayes.Add(term, resultArray);

        }
        static private void UpdateTermTdfIdfCategoryEstimates(String term, decimal[] termIDFForCategory)
        {
            for (int i = 0; i < 20; i++)
            {
                termTdfIdfCategoryEstimates[i].Add(term, termIDFForCategory[i]);
            }
        }
        static private void TdfIdfDocumentCategoryEstimator()
        {
            for (int i = 0; i < COLLECTION; i++)
            {
                ArrayList docTermArray;
                int docNum = i + 1;
                String documentID = docNum.ToString();
                documents.TryGetValue(documentID, out docTermArray);
                if (docTermArray != null)
                {
                    int max = FindMaxTdfIdfCategory(docTermArray, categoryPR);
                    tdfIdfDocumentCategories[i] = max - 1;
                    //update confusionMatrix
                    int row = assignedDocumentCategories[i];
                    int column = tdfIdfDocumentCategories[i];
                    tdfIdfConfusionMatrix[row][column] += 1;

                }
                else
                    tdfIdfDocumentCategories[i] = -1;

            }
        }        
        static private int FindMaxTdfIdfCategory(ArrayList document, decimal[] categoryPR)
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
                    decimal[] termTdfIdfBayes;
                    decimal termFrequency = Convert.ToDecimal(split[1]);
                    tdfIdfBayes.TryGetValue(split[0], out termTdfIdfBayes);
                    if (termTdfIdfBayes != null)
                        total += ((decimal)termTdfIdfBayes[i] * termFrequency);

                    
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
    }

    
}
