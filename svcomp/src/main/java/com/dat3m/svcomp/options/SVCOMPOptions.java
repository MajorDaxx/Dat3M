package com.dat3m.svcomp.options;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.collect.ImmutableSet;

public class SVCOMPOptions extends Options {

    protected String programFilePath;
    protected Set<String> supportedFormats = ImmutableSet.copyOf(Arrays.asList("litmus", "pts", "bpl")); 

    public SVCOMPOptions(){
        super();
        Option inputOption = new Option("i", "input", true,
                "Path to the file with input program");
        inputOption.setRequired(true);
        addOption(inputOption);
    }
    
    public void parse(String[] args) throws ParseException, RuntimeException {
        CommandLine cmd = new DefaultParser().parse(this, args);

        String inputFilePath = cmd.getOptionValue("input");
        if(supportedFormats.stream().map(f -> inputFilePath.endsWith(f)). allMatch(b -> b.equals(false))) {
            throw new RuntimeException("Unrecognized program format");
        }
        programFilePath = cmd.getOptionValue("input");
    }

    public String getProgramFilePath() {
        return programFilePath;
    }
}
