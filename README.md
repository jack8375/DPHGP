The document provides the source code and its compiled .java files, a library package for ontology parsing and matching, the compiled similarity matrix, and the WordNet tool.
## 1.Environment Configuration
This project is running on IntelliJ IDEA, with the SDK version set to JDK 1.8 (Java 8), and the SDK language configured as SDK default (8).
## 2.Parameter configuration
The parameter settings of this project are consistent with those in "Dual-Population Hybrid Genetic Programming for Decision Forest Optimization in Adaptive Classification Rule Construction".
## 3.Address Configuration
The addresses that need to be modified and configured for this project include: (1) The ontology file path; (2) the similarity matrix path; (3)WordNet tool path.
## 4.Data Set
The benchmark dataset utilized in this study is available for download from the official website at https://oaei.ontologymatching.org/2012/benchmarks/benchmarks.zip.
## 5.Related Description
(1) The **parsing package** contains ontology parsing methods used to retrieve information such as the ontology's ID, label, comment, subclass and superclass, related properties, and standard alignment.  
(2) The **similarity package** includes methods for similarity calculation, which are used to compute similarity between two entities.  
(3) The **Matrix package** contains the functions used for computing the similarity feature matrix.  
(4) The **Function package** provides the function sets used for decision trees and ensemble individuals.  
(5) The **DPHGP package** describes the implementation details of individual encoding, decoding, fitness evaluation functions, and various innovative components. 
