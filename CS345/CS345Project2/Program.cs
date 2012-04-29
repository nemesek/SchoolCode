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
        const int COLLECTION = 19976;
        static int[] categoryNTs = new int[20];
        static Dictionary<String, decimal>[] termCategoryEstimates = new Dictionary<String, decimal>[20];
      
        static void Main(string[] args)
        {
            Dictionary<String, ArrayList> documents = new Dictionary<string,ArrayList>();
            ArrayList[] categories = new ArrayList[20];
            decimal[] categoryPR = new decimal[20];
            int[][] termNJTs = new int[DICTIONARY][];

            InitializeTermCategoryDictionaries();
            BuildCategoryVectors(categories);
            CalculateCategoryProbabilities(categoryPR, categories);
            BuildDocumentVectors(documents);
            //BuildNJTArray(termNJTs, categories, documents);
            int[] njt = CalculateNJT("1", categories, documents);
            int[] njt2 = CalculateNJT("2", categories, documents);
            int[] njt3 = CalculateNJT("3", categories, documents);
            ComputeBayesianEstimates(njt);
            ComputeBayesianEstimates(njt2);
            ComputeBayesianEstimates(njt3);
            UpdateTermCategoryEstimates(njt, "1");
            UpdateTermCategoryEstimates(njt2, "2");
            UpdateTermCategoryEstimates(njt3, "3");
            //UpdateTermCategoryEstimates(njt2);
            Dictionary<String, decimal> testDictionary = termCategoryEstimates[0];
            Dictionary<String, decimal> testDictionary2 = termCategoryEstimates[1];
            Dictionary<String, decimal> testDictionary3 = termCategoryEstimates[2];
            var items = (from k in testDictionary.Keys
                         orderby testDictionary[k] descending
                         select k).Take(5);
            Console.WriteLine("Category 1 Top 5");
            foreach (String k in items)
            {
                Console.WriteLine("{0}: {1}", k, testDictionary[k]);
            }
            var items2 = (from k in testDictionary2.Keys
                         orderby testDictionary2[k] descending
                         select k).Take(5);
            Console.WriteLine("Category 2 Top 5");
            foreach (String k in items2)
            {
                Console.WriteLine("{0}: {1}", k, testDictionary2[k]);
            }
            var items3 = (from k in testDictionary3.Keys
                          orderby testDictionary3[k] descending
                          select k).Take(5);
            Console.WriteLine("Category 3 Top 5");
            foreach (String k in items3)
            {
                Console.WriteLine("{0}: {1}", k, testDictionary3[k]);
            }


            Console.WriteLine("Done");  

        }

        static private void InitializeTermCategoryDictionaries()
        {
            for (int i = 0; i < 20; i++)
            {
                termCategoryEstimates[i] = new Dictionary<String, decimal>();
            }
        }

        static private void UpdateTermCategoryEstimates(int[] njt, String term)
        {
            //int key = Convert.ToInt32(term);
            for (int i = 0; i < 20; i++)
            {
                termCategoryEstimates[i].Add(term, njt[i]);
            }
            
        }
        static private decimal ComputeBayesianEstimates(int[] njt)
        {
            decimal result;
            decimal maxResult = 0;
            int maxCategory = 0;
            for (int i = 0; i < 20; i++)
            {
                
                decimal numerator = (decimal)(njt[i] + 1);
                decimal denominator = (decimal)(categoryNTs[i] + DICTIONARY);
                result = numerator / denominator;
                if (result > maxResult)
                {
                    maxCategory = i + 1;
                    maxResult = result;
                }
            }
            Console.WriteLine("Max category: " + maxCategory.ToString());
            Console.WriteLine(maxResult.ToString());
            return maxResult;
        }

        static private void BuildNJTArray(int[][] termNJTs, ArrayList[] categories, Dictionary<String, ArrayList> documents)
        {
            for (int i = 0; i < DICTIONARY; i++)
            {
                String term = Convert.ToString(i + 1);
                int[] njt = CalculateNJT(term, categories, documents);
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
                Console.WriteLine(categoryPR[i].ToString());
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
                        //int docID = Convert.ToInt32(split[1]);
                        //int termID = Convert.ToInt32(split[0]);
                        //termID--;
                        //int tf = Convert.ToInt32(split[2]);
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
                        if (temp > catNum)
                        {
                            //LinkedList<int> catList = new LinkedList<int>();
                            ArrayList catList = new ArrayList();
                            categories[catNum] = catList;
                            catList.Add(split[0]);
                            catNum = temp;
                        }
                        else
                        {
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
