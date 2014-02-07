package com.sis.uta.puzzleGame.screens;


import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;


public class ReadXMLFile {
	
	 public List<TextPuzzle> getTextPuzzlesByParseXml() throws Exception {
		 
		 List list = new ArrayList<TextPuzzle>();
		 TextPuzzle tp = null;
		 int i = 0;
		 
		 XmlReader xml = new XmlReader();
		 
		 Element root = xml.parse(Gdx.files.internal("data/text.xml"));
		 
		 for(;i<2;i++){
		 Element textpuzzle = root.getChildByName("textpuzzle"+ i);		 
		 tp = new TextPuzzle();
		 tp.setQuestion(textpuzzle.get("question"));
		 tp.setAnsewer1(textpuzzle.get("answer1"));
		 tp.setAnsewer2(textpuzzle.get("answer2"));
		 tp.setAnsewer3(textpuzzle.get("answer3"));
		 tp.setRightanswer(textpuzzle.get("rightanswer"));
		 list.add(tp);
		 }
		 
		 return list;
	     }
	 }