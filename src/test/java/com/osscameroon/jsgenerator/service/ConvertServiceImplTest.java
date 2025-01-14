package com.osscameroon.jsgenerator.service;

import static com.osscameroon.jsgenerator.util.ConstantsTest.HTML_SRC_DIR_TEST;
import static com.osscameroon.jsgenerator.util.ConstantsTest.JS_DEST_DIR_TEST;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.osscameroon.jsgenerator.exception.HTMLFileNotFoundException;
import com.osscameroon.jsgenerator.exception.IncorrectHTMLFileNameException;
import com.osscameroon.jsgenerator.exception.NoHTMLFileNameException;
import com.osscameroon.jsgenerator.model.JSVariableDeclaration;
import com.osscameroon.jsgenerator.util.FileUtil;

/**
 * Provides an implementation of {@link ConvertService} interface for test
 * environment. This class extends {@link ConvertServiceImpl}
 *
 * @author Fanon Jupkwo
 *
 */

public class ConvertServiceImplTest extends ConvertServiceImpl {

    private static final Logger logger = Logger.getLogger(ConvertServiceImplTest.class.getName());

    public ConvertServiceImplTest(JSVariableDeclaration jsVariableDeclaration) {
	super(jsVariableDeclaration);

    }

    /**
     * This method is similar to
     * {@link ConvertServiceImpl#convertHtmlFiletoJsFileFromCommandLineInterface(String)}
     * but adapted for the test environment. Instead of using
     * {@link com.osscameroon.jsgenerator.util.Constants} values, we use the ones
     * of{@link com.osscameroon.jsgenerator.util.ConstantsTest}. These values
     * represent the input and output folders of production and test environments.
     *
     * @throws NoHTMLFileNameException        if htmlFileName is null
     * @throws IncorrectHTMLFileNameException if htmlFileName is incorrect
     * @throws HTMLFileNotFoundException      if the html file is not found
     */

    @Override
    public void convertHtmlFiletoJsFileFromCommandLineInterface(String htmlFileName)
	    throws NoHTMLFileNameException, IncorrectHTMLFileNameException, HTMLFileNotFoundException {

	if (htmlFileName == null) {

	    throw new NoHTMLFileNameException("There is no Html file name.");
	}

	if (!isHTMLFileNameCorrect(htmlFileName)) {

	    throw new IncorrectHTMLFileNameException("The HTML file's name \"" + htmlFileName + "\" is incorrect.");
	}

	// get the full supposed path to the html file

	String pathToHtml = HTML_SRC_DIR_TEST.getFolder().concat(htmlFileName);

	/*
	 * If HTML_SRC_DIR folder doesn't exist then it will create them
	 *
	 * new File(HTML_SRC_DIR.getFolder()).createNewFile();
	 */

	logger.log(Level.INFO, " **** Converting " + htmlFileName + " to js file **** ");

	/*
	 * By default, the input folder exists but the output folder don't. So, if
	 * JS_DEST_DIR folder doesn't exist then it will be created in order to receive
	 * generated Js files. If this output folder doesn't exist when this method is
	 * called, an exception will be thrown.
	 */

	File outputFolder = new File(JS_DEST_DIR_TEST.getFolder());

	if (outputFolder.exists() && outputFolder.isDirectory()) {

	} else {

	    outputFolder.mkdir();
	}

	// get the full supposed path to the js file

	String jsFilePath = JS_DEST_DIR_TEST.getFolder().concat(htmlFileName.split(".html")[0] + ".js");

	String htmlContent = FileUtil.readHtmlFile(pathToHtml).toString();
	String jsContent = convert(htmlContent);
	FileUtil.writeJsFile(jsContent, jsFilePath);

	logger.log(Level.INFO, " **** Conversion complete **** ");

    }

}
