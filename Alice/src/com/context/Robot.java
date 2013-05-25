package com.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import bitoflife.chatterbean.util.Translate;

public class Robot {

	private ChartManager chartManager = null;

	public Robot() {
		chartManager = ChartManager.getInstance();
	}

	public String getResponseByInput(String input) {
		input = Translate.translateString(input);
		return chartManager.response(input);
	}

	public static void main(String[] args) throws IOException {
		Robot demo = new Robot();
		BufferedReader br = getInputBFReader();
		String input;
		while ((input = br.readLine()) != null) {
			System.out.println("you say>" + input);
			System.out.println("Alice>" + demo.getResponseByInput(input));
		}
	}

	private static BufferedReader getInputBFReader() {
		String inputPath = "./myinput.txt";
		File file = new File(inputPath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;

	}
}
