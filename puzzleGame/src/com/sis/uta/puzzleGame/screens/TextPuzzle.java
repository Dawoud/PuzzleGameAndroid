package com.sis.uta.puzzleGame.screens;

public class TextPuzzle {
	private int id;
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String rightanswer;
	
	public TextPuzzle(int id,String question,String answer1,
			String answer2,String answer3,String rightanswer){
		super();
		this.id = id;
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.rightanswer = rightanswer;
	}
	
	public TextPuzzle()
	{
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnsewer1() {
		return answer1;
	}
	public void setAnsewer1(String ansewer1) {
		this.answer1 = ansewer1;
	}
	public String getAnsewer2() {
		return answer2;
	}
	public void setAnsewer2(String ansewer2) {
		this.answer2 = ansewer2;
	}
	public String getAnsewer3() {
		return answer3;
	}
	public void setAnsewer3(String ansewer3) {
		this.answer3 = ansewer3;
	}
	public String getRightanswer() {
		return rightanswer;
	}
	public void setRightanswer(String rightanswer) {
		this.rightanswer = rightanswer;
	}

	@Override
	public String toString() {
		return question + "" + answer1 + "" + answer2 + "" + answer3 + "" + rightanswer;
  }
	
}
