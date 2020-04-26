# backpropagation

A kovetkezo lepesek szuksegsek a Backpropagation szamfelismeres feluletenek elinditasahoz: 

1. Mintak kigeneralasa.

	javac GeneratePatterns.java
	java GeneratePatterns

	Kigeneralja a binaris megfelelojet a szoveges mintaknak melyeket a:
		1.txt, 2.txt ... 9.txt szoveges allomanyokbol beolvas.
	A kimeneti allomany neve:
		patterns.src

2. Neuron halo betanitasa.
	
	javac Learn.java
	java Learn
	
	Tanitjuk a halozatot Backpropagationt alkalmazva az elso lepesben megadott mintahalmazra.
	A megtanitott halozatot kimentjuk a trainedNetwork.src binaris allomanyba.
	Ha befejezodott a tanitas a konzolra kiirjuk a tantasi ciklusok szamat.

3. GUI elinditasa

	javac NumberBackpropagationGui.java
	java NumberBackpropagationGui

	Elindit egy GUI-t melyen ki lehet valasztani a betanitott mintak kozul egyiket. A feluleten lehet modositani a mintat
	es a Minta kiertekelese button segitsegevel a Backpropagationnel tanitott neuron halo megprobalja megfejteni a bementi mintankat.
	A megfejteshez az elozo lepesben betanitott neuron halot hasznaljuk.(trainedNetwork.src allomanybol beolvasva)
	
