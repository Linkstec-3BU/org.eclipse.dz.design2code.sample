package org.eclipse.dz.design2code.sample;

import javafx.beans.property.SimpleStringProperty;

public class EditArea {

	private final SimpleStringProperty flg;
	private final SimpleStringProperty logicOne;
	private final SimpleStringProperty logicTwn;
	private final SimpleStringProperty logicThree;
	private final SimpleStringProperty editArea;
	private final SimpleStringProperty comment;

	public EditArea(String flg,String logicOne, String logicTwn, String logicThree,
			String editArea, String comment) {
		this.flg = new SimpleStringProperty(flg);
		this.logicOne = new SimpleStringProperty(logicOne);
		this.logicTwn = new SimpleStringProperty(logicTwn);
		this.logicThree = new SimpleStringProperty(logicThree);
		this.editArea = new SimpleStringProperty(editArea);
		this.comment = new SimpleStringProperty(comment);
	}

	public String getFlg() {
		return flg.get();
	}
	
	public String getComment() {
		return comment.get();
	}

	public String getEditArea() {
		return editArea.get();
	}

	public String getLogicOne() {
		return logicOne.get();
	}

	public String getLogicThree() {
		return logicThree.get();
	}

	public String getLogicTwn() {
		return logicTwn.get();
	}

	public void setFlg(String flg) {
		this.flg.set(flg);
	}
	
	public void setComment(String comment) {
		this.comment.set(comment);
	}

	public void setEditArea(String editArea) {
		this.editArea.set(editArea);
	}

	public void setLogicOne(String logicOne) {
		this.logicOne.set(logicOne);
	}

	public void setLogicThree(String logicThree) {
		this.logicThree.set(logicThree);
	}

	public void setLogicTwn(String logicTwn) {
		this.logicTwn.set(logicTwn);
	}
}
