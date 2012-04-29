https://blackboard.olemiss.edu/bbcswebdav/courses/Csci_345_Section_1_CHEN_2011-2012_SPRG/project2.pdf

1
CSCI 345: Information Storage and Retrieval Assigned: March 30
Project 2: Document Classication Due: April 27
This project deals with building a naive Bayes classier for document classication. You are given a set of 19; 976
documents, belonging to 20 newsgroups. The documents and lables are specied by two text les, category and
wordvector. Each line in category contains two numbers: DocID and CatID. For example, \1001 2" is a line
in category. It denotes that document 1001 belongs to category 2 (newsgroup 2). Each line in wordvector
consists of three numbers: TermID, DocID, and TermFreq. For example, \7 2 4" is a line in wordvector. It
denotes that Term7 appears in document 2, and the number of appearances is 4, i.e., TF7 = 4 where TF stands
for term frequency. The correspondence between a term and TermID is given by another le, vocabulary. For
example, the 12th line in vocabulary contains the term \snake", hence its TermID is 12, i.e., Term12 = snake.
The compressed archive is available at http://www.cs.olemiss.edu/ychen/data/csci345 pj2.zip.
Using the above dataset and the \dice-dice" model discussed in the class, you need to build a Bayes classier.
In particular:
1. Compute the Bayesian estimates (with Beta prior) of the condition probabilities, i.e.,
Pr(Termj jcategoryt) =
njt + 1
Nt +M
where njt is the total number of appearances of Termj in category t, Nt is the number of terms in all
documents from category t, M is the size of the dictionary. Using your estimates of the condition and prior
probabilities (prior is obtained from the maximum likelihood estimate), predict the category memebership
for all documents based on
yi = arg max
t
ln Pr(categoryt) +
MX
j=1
xij ln Pr(Termj jcategoryt)
where xi = [xi1; xi2; : : : ; xiM] is the term frequency vector for documenti. To evaluate the performance of
your classier, compute the confusion matrix, C = [Cij ], dened as follows:
Cij = number of categoryi documents classied as categoryj :
Find out the average precision pave,
pave =
1
20
X20
i=1
Cii P20
j=1 Cij
:
For each newsgroup t, identify the top 20 terms with the highest Pr(Termj jcategoryt).
2. Repeat the above process with the tdf weights, i.e., each document is represented by a vector of tdf weights.
Do the Bayesian estimation of the condition probabilities. Evaluate the performance of the classier using the
confusion matrix and the average precision. Identify the top 20 terms with the highest Pr(tdfj jcategoryt)
for each newsgroup t.
3. Compare the top 20 terms identied using tf with those identied using tdf. Discuss your observations.
Describe the topic of each newsgroup based on these top terms.
4. Select top d% of the terms according to their idf weights. For d = 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45,
40, 35, and 30, construct a Bayesian classier as in step 1 using only these selected terms. Compute pave
for each value of d. Discuss your results.
5. Prepare a report. The report should include a cover page, a detailed description of your implementation
of the classier and evaluation process. Include in your report all the required results (confusion matrices,
pave, top 20 terms for each newsgroup) as well as the required discussions. Please DO NOT include a hard
copy of the source code in the report. The report is due Friday, April 27. You should also send your
source code les via email to the instructor.