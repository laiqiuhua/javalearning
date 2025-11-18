package com.learning.javalearning.pmml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.dmg.pmml.PMML;
import org.jpmml.lightgbm.GBDT;
import org.jpmml.lightgbm.HasLightGBMOptions;
import org.jpmml.lightgbm.LightGBMUtil;
import org.jpmml.lightgbm.ObjectiveFunction;
import org.jpmml.model.metro.MetroJAXBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightGBMConvertExample {

	@Parameter (
			names = {"--help"},
			description = "Show the list of configuration options and exit",
			help = true
	)
	private boolean help = false;

	@Parameter (
			names = {"--lgbm-input"},
			description = "LightGBM text input file",
			required = true
	)
	private File input = null;

	@Parameter (
			names = {"--pmml-output"},
			description = "PMML output file",
			required = true
	)
	private File output = null;

	@Parameter (
			names = {"--objective"},
			description = "Custom objective function"
	)
	private String objectiveFunction = null;

	@Parameter (
			names = {"--target-name"},
			description = "Target name. Defaults to \"_target\""
	)
	private String targetName = null;

	@Parameter (
			names = {"--target-categories"},
			description = "Target categories. Defaults to 0-based index [0, 1, .., num_class - 1]"
	)
	private List<String> targetCategories = null;

	@Parameter (
			names = {"--X-" + HasLightGBMOptions.OPTION_COMPACT},
			description = "Transform LightGBM-style trees to PMML-style trees",
			arity = 1
	)
	private boolean compact = true;

	@Parameter (
			names = {"--X-" + HasLightGBMOptions.OPTION_NAN_AS_MISSING},
			description = "Treat Not-a-Number (NaN) values as missing values",
			arity = 1
	)
	private boolean nanAsMissing = true;

	@Parameter (
			names = {"--X-" + HasLightGBMOptions.OPTION_NUM_ITERATION},
			description = "Limit the number of trees. Defaults to all trees"
	)
	private Integer numIteration = null;


	static
	public void main(String... args) throws Exception {
		LightGBMConvertExample lightGBMConvertExample = new LightGBMConvertExample();

		JCommander commander = new JCommander(lightGBMConvertExample);
		commander.setProgramName(LightGBMConvertExample.class.getName());

		try {
			commander.parse(args);
		} catch(ParameterException pe){
			StringBuilder sb = new StringBuilder();

			sb.append(pe.toString());
			sb.append("\n");

			commander.usage(sb);

			System.err.println(sb.toString());

			System.exit(-1);
		}

		if(lightGBMConvertExample.help){
			StringBuilder sb = new StringBuilder();

			commander.usage(sb);

			System.out.println(sb.toString());

			System.exit(0);
		}

		lightGBMConvertExample.run();
	}

	private void run() throws Exception {
		GBDT gbdt;

		try(InputStream is = new FileInputStream(this.input)){
			logger.info("Loading GBDT..");

			long begin = System.currentTimeMillis();
			gbdt = LightGBMUtil.loadGBDT(is);
			long end = System.currentTimeMillis();

			logger.info("Loaded GBDT in {} ms.", (end - begin));
		} catch(Exception e){
			logger.error("Failed to load GBDT", e);

			throw e;
		}

		if(this.objectiveFunction != null){
			logger.info("Setting custom objective function");

			ObjectiveFunction objectiveFunction = LightGBMUtil.parseObjectiveFunction(this.objectiveFunction);

			gbdt.setObjectiveFunction(objectiveFunction);
		}

		Map<String, Object> options = new LinkedHashMap<>();
		options.put(HasLightGBMOptions.OPTION_COMPACT, this.compact);
		options.put(HasLightGBMOptions.OPTION_NAN_AS_MISSING, this.nanAsMissing);
		options.put(HasLightGBMOptions.OPTION_NUM_ITERATION, this.numIteration);

		PMML pmml;

		try {
			logger.info("Converting GBDT to PMML..");

			long begin = System.currentTimeMillis();
			pmml = gbdt.encodePMML(options, this.targetName, this.targetCategories);
			long end = System.currentTimeMillis();

			logger.info("Converted GBDT to PMML in {} ms.", (end - begin));
		} catch(Exception e){
			logger.error("Failed to convert GBDT to PMML", e);

			throw e;
		}

		try(OutputStream os = Files.newOutputStream(this.output.toPath())){
			logger.info("Marshalling PMML..");

			long begin = System.currentTimeMillis();
			MetroJAXBUtil.marshalPMML(pmml, os);
			long end = System.currentTimeMillis();

			logger.info("Marshalled PMML in {} ms.", (end - begin));
		} catch(Exception e){
			logger.error("Failed to marshal PMML", e);

			throw e;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(LightGBMConvertExample.class);
}