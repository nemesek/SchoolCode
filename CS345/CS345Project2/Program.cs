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
        static void Main(string[] args)
        {
            Dictionary<String, ArrayList> documents = new Dictionary<string,ArrayList>();
            ArrayList[] categories = new ArrayList[20];
            decimal[] categoryPR = new decimal[20];
            int[][] termNJTs = new int[DICTIONARY][];


            BuildCategoryVectors(categories);
            CalculateCategoryProbabilities(categoryPR, categories);
            BuildDocumentVectors(documents);
            BuildNJTArray(termNJTs, categories, documents);
            //int[] njt = CaclulateNJT("1", categories, documents);


            Console.WriteLine("Done");  

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
                        int docID = Convert.ToInt32(split[1]);
                        int termID = Convert.ToInt32(split[0]);
                        termID--;
                        int tf = Convert.ToInt32(split[2]);
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
