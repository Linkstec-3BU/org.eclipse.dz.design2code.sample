package org.eclipse.dz.design2code.sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ExcelEditTool extends Application {

	private final ObservableList<EditArea> editAreaData = FXCollections.observableArrayList(
			new EditArea("", "1", "2", "3", "4", "5"), new EditArea("", "", "", "", "", ""),
			new EditArea("", "", "", "", "", ""), new EditArea("", "", "", "", "", ""),
			new EditArea("", "", "", "", "", ""), new EditArea("", "", "", "", "", ""),
			new EditArea("", "", "", "", "", ""));

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Table View Sample");

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 20));

		final Label actionTaken = new Label();
		TableView<EditArea> table = new TableView<>();
		table.setEditable(true);

		// 番号列設定
		TableColumn<EditArea, EditArea> numberCol = new TableColumn<>("No");
		numberCol.setMinWidth(20);
		numberCol.setCellValueFactory(new Callback<CellDataFeatures<EditArea, EditArea>, ObservableValue<EditArea>>() {
			@SuppressWarnings("rawtypes")
			@Override
			public ObservableValue<EditArea> call(CellDataFeatures<EditArea, EditArea> p) {
				return new ReadOnlyObjectWrapper(p.getValue());
			}
		});

		numberCol.setCellFactory(new Callback<TableColumn<EditArea, EditArea>, TableCell<EditArea, EditArea>>() {
			@Override
			public TableCell<EditArea, EditArea> call(TableColumn<EditArea, EditArea> param) {
				return new TableCell<EditArea, EditArea>() {
					@Override
					protected void updateItem(EditArea item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							setText(this.getTableRow().getIndex() + 1 + "");
						} else {
							setText("");
						}
					}
				};
			}
		});
		numberCol.setSortable(false);

		ContextMenu logicContext = new ContextMenu();

		MenuItem[] items1 = new MenuItem[] { new MenuItem("行追加"), new MenuItem("行削除")};

		Menu addLogicMenu = new Menu("処理内容追加");
		addLogicMenu.getItems().add(new MenuItem("処理内容1(セット)"));
		addLogicMenu.getItems().add(new MenuItem("処理内容2(IFロジック)"));
		addLogicMenu.getItems().add(new MenuItem("処理内容3(FORループ)"));
		addLogicMenu.getItems().add(new MenuItem("処理終了"));

		Menu removeLogicMenu = new Menu("処理内容削除");
		MenuItem removeSet1 = new MenuItem("処理内容1(セット)削除");
		MenuItem removeSet2 = new MenuItem("処理内容2(IFロジック)削除");
		removeSet2.setDisable(true);
		MenuItem removeSet3 = new MenuItem("処理内容3(FORループ)削除");
		removeSet3.setDisable(true);
		removeLogicMenu.getItems().add(removeSet1);
		removeLogicMenu.getItems().add(removeSet2);
		removeLogicMenu.getItems().add(removeSet3);

		Menu logicMenu = new Menu("処理内容入力");
		logicMenu.getItems().add(new MenuItem("「A」を「B」に設定する"));
		logicMenu.getItems().add(new MenuItem("「A」=式"));
		logicMenu.getItems().add(new MenuItem("「A」を実行する"));
		logicMenu.getItems().add(new MenuItem("処理終了"));

		logicContext.getItems().addAll(items1);
		logicContext.getItems().addAll(addLogicMenu);
		logicContext.getItems().addAll(removeLogicMenu);
		logicContext.getItems().addAll(logicMenu);

		TableColumn<EditArea, String> logicOneCol = new TableColumn<>("ロジック1");
		logicOneCol.setMinWidth(200);
		logicOneCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("logicOne"));

		TableColumn<EditArea, String> logicTwnCol = new TableColumn<>("ロジック2");
		logicTwnCol.setMinWidth(200);
		logicTwnCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("logicTwn"));

		TableColumn<EditArea, String> logicThreeCol = new TableColumn<>("ロジック3");
		logicThreeCol.setMinWidth(200);
		logicThreeCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("logicThree"));

		TableColumn<EditArea, String> sencondNameCol = new TableColumn<>("ロジック処理内容");
		sencondNameCol.setMinWidth(200);
		sencondNameCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("editArea"));

		TableColumn<EditArea, String> commentCol = new TableColumn<>("コメント");
		commentCol.setMinWidth(200);
		commentCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("comment"));

		table.getColumns().addAll(numberCol, logicOneCol, logicTwnCol, logicThreeCol, sencondNameCol, commentCol);

		table.setItems(editAreaData);

		table.setRowFactory(new Callback<TableView<EditArea>, TableRow<EditArea>>() {
			@Override
			public TableRow<EditArea> call(TableView<EditArea> param) {
				TableRow<EditArea> row = new TableRow<>();
				row.setOnMousePressed(e -> {
					if (e.isSecondaryButtonDown()) {
						logicContext.show(row, e.getScreenX(), e.getScreenY());
						if (editAreaData.get(row.getIndex()).getLogicOne() == "IF") {
							removeSet2.setDisable(false);
						} else if (editAreaData.get(row.getIndex()).getLogicOne() == "FOR") {
							removeSet3.setDisable(false);
						} else {
							removeSet2.setDisable(true);
							removeSet3.setDisable(true);
						}
						addMenuLogic(stage, addLogicMenu, row.getIndex());
						removeMenuLogic(removeLogicMenu, row.getIndex());
						menuLogic(logicContext, row.getIndex());
						menuLogic2(stage, logicMenu, row.getIndex());
					}
				});
				return row;
			}
		});

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(label, table, actionTaken);
		VBox.setVgrow(table, Priority.ALWAYS);

		stage.setScene(new Scene(vbox));
		stage.show();

	}

	private void addMenuLogic(Stage stage, Menu logicMenu, int index) {
		for (int i = 0; i < logicMenu.getItems().size(); i++) {
			MenuItem subMenu = logicMenu.getItems().get(i);
			if (subMenu.getText() == "処理内容1(セット)") {
				subMenu.setOnAction(ex -> {
					openDialog1(stage, index);
				});
			} else if (subMenu.getText() == "処理内容2(IFロジック)") {
				subMenu.setOnAction(ex -> {
					openDialog2(stage, index);
				});
			} else if (subMenu.getText() == "処理内容3(FORループ)") {
				subMenu.setOnAction(ex -> {
					openDialog3(stage, index);
				});
			} else if (subMenu.getText() == "処理終了") {
				subMenu.setOnAction(ex -> {
					EditArea edit = new EditArea("", "", "", "", "処理終了", "");
					editAreaData.add(index, edit);
				});
			}
		}
	}

	private void removeMenuLogic(Menu logicMenu, int index) {
		for (int i = 0; i < logicMenu.getItems().size(); i++) {
			MenuItem subMenu = logicMenu.getItems().get(i);
			ObservableList<EditArea> ifLogicData = FXCollections.observableArrayList();
			if (subMenu.getText() == "処理内容1(セット)削除") {
				subMenu.setOnAction(ex -> {
					editAreaData.remove(index);
				});
			} else if (subMenu.getText() == "処理内容2(IFロジック)削除") {
				subMenu.setOnAction(ex -> {
					for (int j = index; j < editAreaData.size(); j++) {
						EditArea edit = editAreaData.get(j);
						if (!"IF".equals(edit.getFlg().substring(0,2))) {
							break;
						}
						ifLogicData.add(edit);
					}
					editAreaData.removeAll(ifLogicData);
				});
			} else if (subMenu.getText() == "処理内容3(FORループ)削除") {
				subMenu.setOnAction(ex -> {
					for (int j = index; j < editAreaData.size(); j++) {
						EditArea edit = editAreaData.get(j);
						if (edit.getFlg() != "FOR") {
							break;
						}
						ifLogicData.add(edit);
					}
					editAreaData.removeAll(ifLogicData);
				});
			}
		}

	}

	private void menuLogic(ContextMenu logicContext, int index) {
		for (int i = 0; i < logicContext.getItems().size(); i++) {
			MenuItem subMenu = logicContext.getItems().get(i);
			if (subMenu.getText() == "行追加") {
				subMenu.setOnAction(ex -> {
					String flg = "";
					if (index > 0) {
						EditArea beforeEdit = editAreaData.get(index - 1);
						flg = beforeEdit.getFlg();
					}
					EditArea edit = new EditArea(flg, "", "", "", "", "");
					editAreaData.add(index, edit);
				});
			} else if (subMenu.getText() == "行削除") {
				subMenu.setOnAction(ex -> {
					editAreaData.remove(index);
				});
			}
		}
	}

	private void menuLogic2(Stage stage, Menu logicMenu, int index) {
		for (int i = 0; i < logicMenu.getItems().size(); i++) {
			MenuItem subMenu = logicMenu.getItems().get(i);
			subMenu.setOnAction(ex -> {
				openDialog4(stage, index,subMenu.getText());
			});
		}
	}

	private String openDialog1(Stage stage, int index) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("項目選択");

		Label label1 = new Label("項目1を入力してください:");
		label1.setMaxSize(200, 20);
		TextField text1 = new TextField();
		text1.setMaxSize(200, 20);
		Label label2 = new Label("項目2を入力してください:");
		label2.setMaxSize(200, 20);
		TextField text2 = new TextField();
		text2.setMaxSize(200, 20);
		
		Button btn = new Button();
		btn.setText("登録");
		btn.setOnAction(e -> {
			String str = "「A」を「B」に設定する".replace("A", text1.getText()).replace("B", text2.getText());
			String flg = "";
			if (index > 0) {
				EditArea beforeEdit = editAreaData.get(index - 1);
				flg = beforeEdit.getFlg();
			}
			EditArea edit = new EditArea(flg, "", "", "", str, "");
			editAreaData.add(index, edit);
			newStage.close();
		});

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		vbox.getChildren().addAll(label1,text1,label2, text2, btn);

		newStage.setScene(new Scene(vbox));
		newStage.show();

		return "";
	}

	private String openDialog2(Stage stage, int index) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("項目選択");

		Label label1 = new Label("条件を入力してください:");
		label1.setMaxSize(200, 20);
		TextField text1 = new TextField();
		text1.setMaxSize(200, 20);
		

		Button btn = new Button();
		btn.setText("登録");
		btn.setOnAction(e -> {
			EditArea edit = editAreaData.get(index);
			String flg =edit.getFlg();
			ObservableList<EditArea> ifLogicData = FXCollections.observableArrayList();
			if (flg == "IF") {				
				ifLogicData.addAll(new EditArea("IF2", "", "IF", "", text1.getText(), ""), new EditArea("IF2", "", "", "", "", ""),
						new EditArea("IF2", "", "", "", "", ""), new EditArea("IF2", "", "ELSE", "", "", ""),
						new EditArea("IF2", "", "", "", "", ""), new EditArea("IF2", "", "", "", "", ""),
						new EditArea("IF2", "", "END", "", "", ""));
			} else if (flg == "IF2") {
				ifLogicData.addAll(new EditArea("IF3", "", "", "IF", text1.getText(), ""), new EditArea("IF3", "", "", "", "", ""),
						new EditArea("IF3", "", "", "", "", ""), new EditArea("IF3", "", "", "ELSE", "", ""),
						new EditArea("IF3", "", "", "", "", ""), new EditArea("IF3", "", "", "", "", ""),
						new EditArea("IF3", "", "", "END", "", ""));
			} else {
				ifLogicData.addAll(new EditArea("IF", "IF", "", "", text1.getText(), ""), new EditArea("IF", "", "", "", "", ""),
						new EditArea("IF", "", "", "", "", ""), new EditArea("IF", "ELSE", "", "", "", ""),
						new EditArea("IF", "", "", "", "", ""), new EditArea("IF", "", "", "", "", ""),
						new EditArea("IF", "END", "", "", "", ""));
			}
			

			editAreaData.addAll(index, ifLogicData);
			newStage.close();
		});

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		vbox.getChildren().addAll(label1,text1, btn);

		newStage.setScene(new Scene(vbox));
		newStage.show();

		return "";
	}

	private String openDialog3(Stage stage, int index) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("項目選択");

		Label label1 = new Label("対象リスト項目を入力してください:");
		label1.setMaxSize(200, 20);
		TextField text1 = new TextField();
		text1.setMaxSize(200, 20);
		
		Button btn = new Button();
		btn.setText("登録");
		btn.setOnAction(e -> {

			ObservableList<EditArea> ifLogicData = FXCollections.observableArrayList(
					new EditArea("FOR", "FOR", "", "", text1.getText(), ""), new EditArea("FOR", "", "", "", "", ""),
					new EditArea("FOR", "", "", "", "", ""), new EditArea("FOR", "", "", "", "", ""),
					new EditArea("FOR", "END", "", "", "", ""));

			editAreaData.addAll(index, ifLogicData);
			newStage.close();
		});

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		vbox.getChildren().addAll(label1,text1, btn);

		newStage.setScene(new Scene(vbox));
		newStage.show();

		return "";
	}
	
	
	private String openDialog4(Stage stage, int index,String str) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("項目選択");

		Label label1 = new Label("項目1を入力してください:");
		label1.setMaxSize(200, 20);
		TextField text1 = new TextField();
		text1.setMaxSize(200, 20);
		Label label2 = new Label("項目2を入力してください:");
		label2.setMaxSize(200, 20);
		TextField text2 = new TextField();
		text2.setMaxSize(200, 20);

		Button btn = new Button();
		btn.setText("登録");
		btn.setOnAction(e -> {
			String str1 = str.replace("A", text1.getText()).replace("B", text2.getText());
			EditArea edit = editAreaData.get(index);
			edit.setEditArea(str1);
			editAreaData.set(index, edit);
			newStage.close();
		});

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		vbox.getChildren().addAll(text1, text2, btn);

		newStage.setScene(new Scene(vbox));
		newStage.show();

		return "";
	}


}
