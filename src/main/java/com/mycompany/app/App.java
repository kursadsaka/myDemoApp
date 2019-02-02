package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
	public static boolean searchAndSum(ArrayList<Integer> array, int e, ArrayList<Integer> array2, int e2) {
		System.out.println("inside searchAndSum");
		boolean islem1 = false;
		boolean islem2 = false;
		int sum = 0;

		if (array != null)
			for (int elt : array)
				if (elt == e * e)
					islem1 = true;

		if (array2 != null)
			for (int elt : array2)
				sum += elt * e;

		if (sum == e2)
			islem2 = true;

		return islem1 && islem2;

	}

	public static void main(String[] args) {
		port(getHerokuAssignedPort());

		get("/", (req, res) -> "Hello, World");

		post("/compute", (req, res) -> {
			// System.out.println(req.queryParams("input1"));
			// System.out.println(req.queryParams("input2"));

			String input1 = req.queryParams("input1");
			java.util.Scanner sc1 = new java.util.Scanner(input1);
			sc1.useDelimiter("[;\r\n]+");
			java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
			while (sc1.hasNext()) {
				int value = Integer.parseInt(sc1.next().replaceAll("\\s", ""));
				inputList.add(value);
			}
			System.out.println(inputList);

			String input2 = req.queryParams("input2").replaceAll("\\s", "");
			int input2AsInt = Integer.parseInt(input2);
			System.out.println(input2AsInt);

			String input3 = req.queryParams("input3");
			java.util.Scanner sc2 = new java.util.Scanner(input3);
			sc2.useDelimiter("[;\r\n]+");
			java.util.ArrayList<Integer> inputList2 = new java.util.ArrayList<>();
			while (sc2.hasNext()) {
				int value = Integer.parseInt(sc2.next().replaceAll("\\s", ""));
				inputList2.add(value);
			}
			System.out.println(inputList2);

			String input4 = req.queryParams("input4").replaceAll("\\s", "");
			int input4AsInt = Integer.parseInt(input4);
			System.out.println(input4AsInt);

			boolean result = App.searchAndSum(inputList, input2AsInt, inputList2, input4AsInt);

			Map map = new HashMap();
			map.put("result", result);
			return new ModelAndView(map, "compute.mustache");
		}, new MustacheTemplateEngine());

		get("/compute", (rq, rs) -> {
			Map map = new HashMap();
			map.put("result", "not computed yet!");
			return new ModelAndView(map, "compute.mustache");
		}, new MustacheTemplateEngine());
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
	}
}
