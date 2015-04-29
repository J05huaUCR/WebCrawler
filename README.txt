Running the Crawler:
--------------------
The Crawler Java program has been packaged to run stand alone on a Linux machine. A
shell script is not utilized as not all parameters are necessary to run the program, therefore
making it difficult to enforce dynamic passing of parameters into the executable. We have
implemented a flag system for allow parameter passing with maximum flexibility so that
parameters can be specified in any order or not at all – save for the input file parameter which is required to run.

The resulting command line execution is of the format:

java -jar Crawler.jar -f <input file>

Optional parameters that can be passed in include:
	-h <max # of hops (default = 3)>
	-t <max # threads (default = 10)>
	-p <max # of pages (default = 10,000)>
	-o <output directory name (default = “output”)>
	
For example, the following commands with execute the Crawler with various specified
parameters:

	1. java -jar Crawler.jar -f seeds.txt -o downloads -h 3 -t 20
	2. java -jar Crawler.jar -f seeds.txt
	
Command #1 will execute the WebCrawler on seeds.txt, create an output folder called
“downloads” in the executable's directory, set a maximum of 3 hops from the URL's in the
seeds.txt file, and instantiate no more than 20 simultaneous threads. The default value of 10,000
pages downloaded is set as the parameter was not set. Command #2 opts to just run the
Crawler with just the required input file name, accepting all other parameters as the default
values.

Running the Indexer:
--------------------
The Indexer Java program has been packaged to run stand alone on a Linux machine. A shell
script is not utilized as not all parameters are necessary to run the program, therefore making it
difficult to enforce dynamic passing of parameters into the executable. We have implemented a
flag system for allow parameter passing with maximum flexibility so that parameters can be
specified in any order or not at all – save for the input file parameter which is required to run.

The resulting command line execution is of the format:

java ­jar Indexer.jar ­i <input directory> ­o <input directory>

For example, the following commands with execute the Indexer with various specified
parameters:

	1. java ­jar Indexer.jar -­i html ­-o index1
	2. java ­jar Indexer.jar -­i downloads ­-o index2
	
Command #1 will execute the Indexer on the directory html, and create an output folder called
index1 in the executable's directory. Command #2 will execute the Indexer on the directory
downloads, and create an output folder called index2 in the executable's directory.
