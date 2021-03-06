/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parker.mips.compiler;

import org.parker.mips.compiler.preprocessor.statements.INCLUDE;
import org.parker.mips.compiler.preprocessor.statements.IF;
import org.parker.mips.compiler.preprocessor.statements.DEFINE;
import org.parker.mips.compiler.preprocessor.statements.UNDEF;
import org.parker.mips.compiler.preprocessor.statements.Statement;
import org.parker.mips.compiler.preprocessor.statements.DEFINLINE;
import org.parker.mips.compiler.data.UserLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.parker.mips.OptionsHandler;
import org.parker.mips.ResourceHandler;

/**
 *
 * @author parke
 */
public class PreProcessor {

    public static final Statement[] validStatements = {new DEFINE(), new DEFINLINE(), new IF(), new INCLUDE(), new UNDEF()};

    private static ArrayList<Statement> statements;

//    private static ArrayList<UserLine> importFile(UserLine line) {
//        line.line = line.line.replaceFirst("#include ", "");
//        line.line = line.line.trim();
//        return loadFile(line.line, line.realLineNumber);
//    }
    public static ArrayList<UserLine> loadFile(String filePath, int realLineOfFilePath) {
        BufferedReader reader;

        filePath = filePath.replaceAll("\"", "");

        File tempFile = new File(filePath);

        ArrayList<UserLine> file = new ArrayList<UserLine>();
        try {
            reader = new BufferedReader(new FileReader(tempFile));
            String line = reader.readLine();
            while (line != null) {
                file.add(new UserLine(line, realLineOfFilePath));
                line = reader.readLine();
            }
            PreProcessor.logPreProcessorMessage("[Pre Processor] Loaded File: " + tempFile.getAbsolutePath());
        } catch (Exception e) {
            PreProcessor.logPreProcessorError("Cannot read file Specified" + " " + e.getMessage(), realLineOfFilePath);
        }
        return file;
    }

    private static Statement generateStatement(ArrayList<UserLine> file, int index) {

        UserLine currentLine = file.get(index);

        for (Statement validStatement : validStatements) {
            if (validStatement.doesStatementBelongToMe(currentLine)) {
                Statement temp = validStatement.generateStatement(file, index, statements);

                if (!temp.IDENTIFIRE.isEmpty()) {

                    for (int i = 0; i < statements.size(); i++) {
                        if (statements.get(i).IDENTIFIRE.equals(temp.IDENTIFIRE)) {
                            statements.remove(i);
                            PreProcessor.logPreProcessorWarning(temp.IDENTIFIRE + " Has already been defined use undef to undefine the value before creating a new value overwritting existing value", currentLine.realLineNumber);
                        }
                    }

                    statements.add(temp);
                }

                return temp;
            }
        }
        PreProcessor.logPreProcessorError("Not a valid Statement", currentLine.realLineNumber);
        return null;
    }

    private static ArrayList<UserLine> preProcessLine(UserLine line) {

        ArrayList<UserLine> oldLines = new ArrayList<UserLine>();
        ArrayList<UserLine> newLines = new ArrayList<UserLine>();
        oldLines.add(line);

        for (int r = 0; r < 5; r++) {

            for (int i = 0; i < oldLines.size(); i++) {
                ArrayList<UserLine> temp = new ArrayList<UserLine>();
                temp.add(oldLines.get(i));

                for (Statement statement : statements) {
                    if (statement.canModifyNonStatements()) {

                        UserLine currentLine;
                        for (int j = 0; j < temp.size(); j++) {
                            currentLine = temp.get(j);
                            temp.remove(j);
                            //ArrayList<UserLine> secondTemp = statement.parseNonStatement(currentLine);
                            //temp.ensureCapacity(temp.size() + secondTemp.size());
                            temp.addAll(j, statement.parseNonStatement(currentLine));
                        }
                    }
                }
                //oldLines.remove(i);
                newLines.addAll(temp);
            }
            oldLines = newLines;
            newLines = new ArrayList<UserLine>();
        }

//        ArrayList<UserLine> lines = new ArrayList();
//        lines.add(line);
//
//        for (Statement stat : statements) {
//
//            for (int i = 0; i < lines.size(); i++) {
//
//                UserLine currentLine = lines.get(i);
//
//                if (stat.canModifyNonStatements() && !line.line.startsWith("#")) {
//                    currentLine = stat.parseNonStatement(currentLine).get(0);
//                } else if (stat.canModifyDefindedStatements() && line.line.startsWith("#")) {
//                    currentLine = stat.parseNonStatement(currentLine).get(0);
//                }
//                lines.set(i, currentLine);
//            }
//        }
        return oldLines;
    }

    private static String cleanLine(String line) {

        line = line.trim(); //gets the full user line

        if (line.equals("")) {
            return "";
        }

        try {
            if (line.contains(";") /*|| line.contains("#")*/) {
                line = line.split(";")[0];//strips line of any comments
                //line = line.split("#")[0];
            }
        } catch (Exception e) {
            return "";
        }
        if (line.equals("")) {
            return "";
        }
        line = line.replaceAll("\t", " "); //replaces indents with space

        //normalfying instructions
        String start;
        String[] args;

        try {
            start = line.split(" ")[0].trim();
        } catch (Exception e) {
            start = line;
        }
        try {
            args = line.replace(start, "").split(",");
            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].trim();
            }
        } catch (Exception e) {
            args = new String[]{""};
        }

        line = start + " " + String.join(",", args);

        return line.trim();
    }

    public static ArrayList<UserLine> preProcess(ArrayList<UserLine> file) {

        if (file != null) {
            if (OptionsHandler.includeRegDef.value) {
                file.add(0, new UserLine("#include \"" + ResourceHandler.REG_DEF_HEADER_FILE + "\"", -2));
                logPreProcessorMessage("Included regdef.asm");
            }
            if (OptionsHandler.includeSysCallDef.value) {
                file.add(0, new UserLine("#include \"" + ResourceHandler.SYS_CALL_DEF_HEADER_FILE + "\"", -3));
                logPreProcessorMessage("Included syscalldef.asm");
            }
        }

        //file = loadFile("C:\\Users\\parke\\OneDrive\\Documents\\GitHub\\MIPS\\Examples\\snake 2.asm", 1);
        statements = new ArrayList<Statement>();

        ArrayList<UserLine> preProcessedFile = subPreProcess(file, true);

        statements.clear();
        return preProcessedFile;
    }

    private static ArrayList<UserLine> subPreProcess(ArrayList<UserLine> data, boolean firstLayer) { //pre processes data in included data or nested is statements

        ArrayList<UserLine> cleanedData = new ArrayList<UserLine>();
        ArrayList<UserLine> preProcessedData = new ArrayList<UserLine>();

        if (data == null) {
            return preProcessedData;
        }

        for (int i = 0; i < data.size(); i++) {

            UserLine currentLine = data.get(i);

            try {
                currentLine.line = cleanLine(currentLine.line);
            } catch (Exception e) {
                PreProcessor.logPreProcessorError("Cannot Clean Line", currentLine.realLineNumber); //cleans the line (fixes spacing and removes comments)
            }

            if (currentLine.line.equals("") || currentLine.line.equals("/n")) { //if line is empty delete it and move on
                continue;
            }

            cleanedData.add(currentLine);

        }
        if (OptionsHandler.saveCleanedFile.value && firstLayer) {
            writeCleanedFile(cleanedData);
        }

        for (int i = 0; i < cleanedData.size(); i++) {
            UserLine currentLine = cleanedData.get(i);

            if (currentLine.line.startsWith("#")) { //handles new preprossesor statemtns

                Statement statement = generateStatement(cleanedData, i);

                if (statement.canGenerateAddedData()) {
                    preProcessedData.addAll(subPreProcess(statement.getGeneratedAddedData(), false));
                }
                i += statement.getSizeOfStatement() - 1;
                continue;
            }

            preProcessedData.addAll(preProcessLine(currentLine)); //preprocesses line and adds resulting line / lines to pre processed file

        }
        if (OptionsHandler.savePreProcessedFile.value) {
            writePreProcessedFile(preProcessedData);
        }

        return preProcessedData;
    }

    private static void writePreProcessedFile(ArrayList<UserLine> fileInfo) {
        File file = new File(ResourceHandler.COMPILER_PATH + ResourceHandler.FILE_SEPERATOR + "PreProcessedFile.asm");
        writeArrayListOfUserLinesToFile(fileInfo, file);
    }

    private static void writeCleanedFile(ArrayList<UserLine> fileInfo) {
        File file = new File(ResourceHandler.COMPILER_PATH + ResourceHandler.FILE_SEPERATOR + "CleanedFile.asm");
        writeArrayListOfUserLinesToFile(fileInfo, file);
    }

    private static void writeArrayListOfUserLinesToFile(ArrayList<UserLine> data, File file) {

        PrintWriter out = null;

        try {

            out = new PrintWriter(file);
            for (int i = 0; i < data.size(); i++) {

                out.println(data.get(i).line);
            }
            PreProcessor.logPreProcessorMessage(file.getName() + " File Wrote to:" + file.getAbsolutePath());
            out.flush();
        } catch (Exception e) {
            PreProcessor.logPreProcessorError("Unable to write " + file.getName() + " File to:" + file.getAbsolutePath() + " " + e.getMessage());
        } finally {
            try {

            } catch (Exception e) {
                out.flush();

            }
            try {
                out.close();
            } catch (Exception e) {

            }
        }
    }

    public static void logPreProcessorError(String message, int line) {

        ASMCompiler.logCompilerError("[PreProcessor]: on line " + line + " " + message);
    }

    public static void logPreProcessorWarning(String message, int line) {

        ASMCompiler.logCompilerWarning("[PreProcessor]: on line " + line + " " + message);
    }

    public static void logPreProcessorMessage(String message, int line) {

        ASMCompiler.logCompilerMessage("[PreProcessor]: on line " + line + " " + message);
    }

    public static void logPreProcessorError(String message) {

        ASMCompiler.logCompilerError("[PreProcessor]: " + message);
    }

    public static void logPreProcessorWarning(String message) {

        ASMCompiler.logCompilerWarning("[PreProcessor]: " + message);
    }

    public static void logPreProcessorMessage(String message) {

        ASMCompiler.logCompilerMessage("[PreProcessor]: " + message);
    }
}
