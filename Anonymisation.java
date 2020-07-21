package anonymisation;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Launcher.MainPg;
import mappers.AnonymisationRuleMapper;
import mappers.DescTypeMapper;
import meta_data.LineMetaData;
import readers.cfg_reader.CfgReader;
import readers.cfg_reader.CfgReaderFactory;
import readers.document_reader.DocumentReader;
import readers.document_reader.DocumentReaderFactory;
import writers.Writer;
import writers.WriterFactory;

/**
 * Class relative to Anonymisation process
 * 
 * @version 1.0
 * @author Nadir Omega
 * @author ylgn
 */
public class Anonymisation {
	String in, out, descFilePath, anonFilePath;
	String separatorIn = ";";
	String separatorOut = ";";

	public Anonymisation(String in, String out, String descFilePath, String anonFilePath) {
		this.in = in;
		this.out = out;
		this.descFilePath = descFilePath;
		this.anonFilePath = anonFilePath;
	}

	/**
	 * Run the anonymisation process. First of all, there is an initialisation phase
	 * with initialisation of the LineMetaData (list of column). Then, as long as it
	 * will have data to read the program call several method from package mapper in
	 * order to call method from anonymisation.
	 * 
	 * @exception Exception
	 */
	public void doAnonymisation()  {
		DocumentReader csvReader = DocumentReaderFactory.getDocumentReader("CSV", this.in);
		Writer csvWriter;
		try {
			csvWriter = WriterFactory.getDocumentWriter("CSV", out);
			CfgReader descReader = CfgReaderFactory.getCfgReader("JSON", descFilePath);
			CfgReader anonRuleReader = CfgReaderFactory.getCfgReader("JSON", anonFilePath);

			LineMetaData lineInit = descReader.initMetaData();

			LineMetaData lineRef = anonRuleReader.initAnonymisationMetaData(lineInit);

			while (csvReader.checkContainingData() == true) {
				ArrayList<String[]> tempLine = csvReader.readMultipleLine(MainPg.blockSize);

				List<String[]> beforWrite = tempLine.stream().filter((e) -> {
					return DescTypeMapper.verificationMatchWithDesc(lineRef, e);
				}).map((e) -> {
					return AnonymisationRuleMapper.lineAnonymisation(lineRef, e);
				}).collect(Collectors.toList());

				csvWriter.writeFileFromList(beforWrite);
			
			}
			MainPg.logger.info("Annonymisation for file :"
			+ this.in+ "Finished succesfully , out path :"+out);
			System.out.println("Anonymisation succesful ");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			MainPg.logger.error("Annonymisation failed");
			e.printStackTrace();
		}
		
}}
