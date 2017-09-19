<b>Scala for Machine Learning</b> Version <b>0.99.2</b> September 7, 2017<br>
Copyright Patrick Nicolas All rights reserved 2013-2017<br>
=================================================================<br>
<br>
<a href="#overview">Overview</a><br>
<a href="#latestRelease">Latest releases</a><br>
<a href="#documentation">Documentation</a><br>
<a href="#requirements">Minimum requirements</a><br>
<a href="#project">Project</a><br>
<a href="#installation">installation</a><br>
<a href="#build">build</a><br>
<a href="#run">Run examples</a><br>
<a href="#persistency">Persistent models and configurations</a><br>
<a href="#appendix">Appendix</a><br>

<i>Note</i>: Versions 0.99.0 and 0.99.1 are related to respectively the original edition (Dec 2014) and revision (Dec 2015) of the book. The source code is available in the ScalaMl repository
<a href="https://github.com/prnicolas/ScalaMl">ScalaML</a>

<h2 id="overview">Overview</h2>
The source code provides software developers with a broad overview of the difference in machine learning algorithms. The reader is expected to have a good grasp of the Scala programming language along with some knowledge in basic statistics. Experience in data mining and machine learning is not a pre-requisite.<br><br>
Source code guidelines are defined in the companion document <b>SourceCodeGuide.html</b><br>

The examples are related to investment portfolio management and trading strategies. For the readers interested either in mathematics or the techniques implemented in this library, I strongly recommend the following readings:
<ul>
<li>"<i>Machine Learning: A Probabilistic Perspective</i>" K. Murphy - MIT Press - 2012</li>
<li>"<i>The Elements of Statistical Learning</i>" T. Hastie, R. Tibshirani, J. Friedman - Springer - 2001</li>
<li>"<i>Pattern Recognition and Machine Learning</i>" C. Bishop - Springer 2006</li>
<li>"<i>Deep leaning</i>" I. Goodfellow, Y. Bengio, A. Courville - MIT Press - 2017</li>
</ul>
The real-world examples, related to financial and market analysis, used for the sole purpose of illustrating the machine learning techniques. They do not constitute a recommendation or endorsement of any specific investment management or trading techniques.<br>
The Appendix contains an introduction to the basic concepts of investment and trading strategies as well as technical analysis of financial markets.

<h2 id="latestRelease">Latest release</h2>
Here is the list of changes introduced in version 0.99.2 and described in "<i>Scala for Machine Learning - 2nd Edition</i>"
<br>
<h3>New Chapters</h3>
<ul>
<li>Chapter 5 - Dimension Reduction</li>
<li>Chapter 8 - Monte Carlo Inference</li>
<li>Chapter 11 - Deep Learning</li>
<li>Chapter 14 - Multi-armed bandit</li>
<li>Chapter 17 - Apache Spark MLlib</li>
</ul>
<br>
<br>
<h2 id="Documentation">Documentation</h2>
The best approach to learn about any particular learning algorithm is to
<ul>
<li>Read the appropriate chapter (i.e. <i>Chapter 6: Naive Bayes Classifier</i>)</li>
<li>Review source code guidelines used in the book <i>SourceCodeGuide.html</i></li>
<li>Look at the examples related to the chapter (i.e. <i>org/scalaml/supervised/bayes</i>)</li>
<li>Browse through the implementation code (i.e. <i>org/scalaml/supervised/bayes</i>)</li>
</ul>
<h2 id="requirements">Minimum Requirements</h2>
<b>Hardware</b>: 2 CPU core with 4 Gbytes RAM for small datasets to build and run examples.<br>
4 CPU Core and 8+ Gbytes RAM for datasets of size 75,000 or larger and/or with 50 features set or larger
<br>
<b>Operating system</b>: None<br>
<b>Software</b>: JDK 1.8.0_25 or later, Scala 2.11.2 or later (2.11.8) recommended, and SBT 0.13+ (see installation section for deployment.<br>

<h2 id="installationbuild">Installation and Build</h2>
<h3 id="installation">Installation</h3>
The installation and build workflow is described in the following diagram:<br>
<img src="images/8742OS_installation.png" alt="Installation and build"><br>
<b>Eclipse & IntelliJ IDEA</b>
The Scala for Machine Learning library is compatible with IntelliJ IDEA 2016 & 2017 and Eclipse Scala IDE 4.0<br>
Specify link to the source in <i>Project/properties/Java Build Path/Source</i>. The two links should be <i>project_name/src/main/scala</i> and <i>project_name/src/test/scala</i><br>
Add the jars required to build and execute the code within Eclipse <i>Project/properties/Java Build Path/Add External Jars</i>as declared in the <i>project_name/.classpath</i><br>
Update the JVM heap parameters in eclipse.ini file as <i>-Xms512m -Xmx8192m</i> or the maximum allowed on your specific machine.
<br>
<h3 id="build">Build</h3>
<h4>build.sbt</h4>
The Simple Build Too (SBT) has to be used to build the library from the source code using the <i>build.sbt</i> file in the root directory<br>
Executing the examples/test in Scala for Machine Learning require sufficient JVM Heap memory (~2G):<br>
in <i>sbt/conf/sbtconfig.text</i> set Xmx to 2058m or higher, -XX:MaxPermSize to 512m or higher i.e. <b>-Xmx4096m -Xms512m -XX:MaxPermSize=512m</b><br><br>
Build script for <i>Scala for Machine Learning</i>:<br>
To build the Scala for Machine Learning library package<br><i><b> $(ROOT)/sbt clean publish-local</b></i><br>
To build the package including test and resource files<br><i><b> $(ROOT)/sbt clean package</b></i><br>
To run the examples <br><i><b> $(ROOT)/sbt clean test</b></i><br>
To generate scala doc for the library<br><i><b> $(ROOT)/sbt doc</b></i><br>
To generate scala doc for the examples<br><i><b> $(ROOT)/sbt test:doc</b></i><br>
To generate report for compliance to Scala style guide:<br><b><i> $(ROOT)/sbt scalastyle</i></b><br>
To compile all examples:<br><i><b>$(ROOT)/sbt test:compile</b></i><br>
<h4>Maven</h4>
A simple <i>pom.xml</i> is available to build the library and execute the test cases:<br>
<b>$(ROOT)/mvn compile</b> to compile the library<br>
<b>$(ROOT)/mvn test</b> to compile and run the examples

<h2 id="run">Run examples</h2>
<u>Note</u>: As the implementation evolves over-time, few test examples may differ from the original test described in the book. The implementation of the algorithm is not expected to change.
<br>
Contrary to the first edition. the examples in the book are written as test using Scalatest<Br>
<b> $(ROOT)/sbt clean test</b><br>  or
<b>$(ROOT)/mvn test</b><br>
<br>
<h2 id="persistency">Persistent models and configurations</h2>
The package object <b>org.scalaml.core.Design</b> provide the trait (or skeleton implementation) of the persistent model <b>Design.Model</b> and configuration <b>Design.Config</b>.<br>
The persistency mechanisms is implemented for a couple of supervised learning models only for illustration purpose. The reader should be able to implement the persistency for configuration and models for all relevant learning algorithms using the template operator << and >> <br>

<h2 id="appendix">Appendix</h2>
The examples have been built and tested with the following libraries:<br>
<b>Java libraries</b><br>
CRF-Trove_3.0.2.jar<br>
LBFGS.jar<br>
colt.jar<br>
CRF-1.1.jar<br>
commons-math3-3.6.jar<br>
libsvm_sml-3.18.jar<br>
jfreechart-1.0.17/lib/jcommon-1.0.21.jar<br>
jfreechart-1.0.17/lib/jfreechart-1.0.17.jar<br>
junit-4.11.jar<br>
<b>Scala 2.11.8 related libraries</b><br>
com.typesafe/config/1.2.2/bundles/config.jar<br>
akka-actor_2.11-2.3.8.jar<br>
scalatest_2.2.6.jar<br>
spark-assembly-2.1.0-hadoop2.7.0.jar<br>
<br>
===========================================