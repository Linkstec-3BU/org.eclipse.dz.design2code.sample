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

		// �ԍ�
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

		MenuItem[] items1 = new MenuItem[] { new MenuItem("�s�ǉ�"), new MenuItem("�s�폜")
				// ,new MenuItem("�s�R�s�["),
				// new MenuItem("�s�؂���"),
				// new MenuItem("�s�\��t��")
		};

		Menu addLogicMenu = new Menu("�������e�ǉ�");
		addLogicMenu.getItems().add(new MenuItem("�������e1(�Z�b�g)"));
		addLogicMenu.getItems().add(new MenuItem("�������e2(IF���W�b�N)"));
		addLogicMenu.getItems().add(new MenuItem("�������e3(FOR���[�v)"));
		addLogicMenu.getItems().add(new MenuItem("�����I��"));

		Menu removeLogicMenu = new Menu("�������e�폜");
		MenuItem removeSet1 = new MenuItem("�������e1(�Z�b�g)�폜");
		MenuItem removeSet2 = new MenuItem("�������e2(IF���W�b�N)�폜");
		removeSet2.setDisable(true);
		MenuItem removeSet3 = new MenuItem("�������e3(FOR���[�v)�폜");
		removeSet3.setDisable(true);
		removeLogicMenu.getItems().add(removeSet1);
		removeLogicMenu.getItems().add(removeSet2);
		removeLogicMenu.getItems().add(removeSet3);

		Menu logicMenu = new Menu("�������e����");
		logicMenu.getItems().add(new MenuItem("�uA�v���uB�v�ɐݒ肷��"));
		logicMenu.getItems().add(new MenuItem("�uA�v=��"));
		logicMenu.getItems().add(new MenuItem("�uA�v�����s����"));
		logicMenu.getItems().add(new MenuItem("�����I��"));

		logicContext.getItems().addAll(items1);
		logicContext.getItems().addAll(addLogicMenu);
		logicContext.getItems().addAll(removeLogicMenu);
		logicContext.getItems().addAll(logicMenu);

		TableColumn<EditArea, String> logicOneCol = new TableColumn<>("���W�b�N1");
		logicOneCol.setMinWidth(200);
		logicOneCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("logicOne"));

		TableColumn<EditArea, String> logicTwnCol = new TableColumn<>("���W�b�N2");
		logicTwnCol.setMinWidth(200);
		logicTwnCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("logicTwn"));

		TableColumn<EditArea, String> logicThreeCol = new TableColumn<>("���W�b�N3");
		logicThreeCol.setMinWidth(200);
		logicThreeCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("logicThree"));

		TableColumn<EditArea, String> sencondNameCol = new TableColumn<>("���W�b�N�������e");
		sencondNameCol.setMinWidth(200);
		sencondNameCol.setCellValueFactory(new PropertyValueFactory<EditArea, String>("editArea"));

		TableColumn<EditArea, String> commentCol = new TableColumn<>("�R�����g");
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
			if (subMenu.getText() == "�������e1(�Z�b�g)") {
				subMenu.setOnAction(ex -> {
					openDialog1(stage, index);
				});
			} else if (subMenu.getText() == "�������e2(IF���W�b�N)") {
				subMenu.setOnAction(ex -> {
					openDialog2(stage, index);
				});
			} else if (subMenu.getText() == "�������e3(FOR���[�v)") {
				subMenu.setOnAction(ex -> {
					openDialog3(stage, index);
				});
			} else if (subMenu.getText() == "�����I��") {
				subMenu.setOnAction(ex -> {
					EditArea edit = new EditArea("", "", "", "", "�����I��", "");
					editAreaData.add(index, edit);
				});
			}

		}
	}

	private void removeMenuLogic(Menu logicMenu, int index) {
		for (int i = 0; i < logicMenu.getItems().size(); i++) {
			MenuItem subMenu = logicMenu.getItems().get(i);
			ObservableList<EditArea> ifLogicData = FXCollections.observableArrayList();
			if (subMenu.getText() == "�������e1(�Z�b�g)�폜") {
				subMenu.setOnAction(ex -> {
					editAreaData.remove(index);
				});
			} else if (subMenu.getText() == "�������e2(IF���W�b�N)�폜") {
				subMenu.setOnAction(ex -> {
					for (int j = index; j < editAreaData.size(); j++) {
						EditArea edit = editAreaData.get(j);
						if (edit.getFlg() != "IF") {
							break;
						}
						ifLogicData.add(edit);
					}
					editAreaData.removeAll(ifLogicData);
				});
			} else if (subMenu.getText() == "�������e3(FOR���[�v)�폜") {
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
			if (subMenu.getText() == "�s�ǉ�") {
				subMenu.setOnAction(ex -> {
					String flg = "";
					if (index > 0) {
						EditArea beforeEdit = editAreaData.get(index - 1);
						flg = beforeEdit.getFlg();
					}
					EditArea edit = new EditArea(flg, "", "", "", "", "");
					editAreaData.add(index, edit);
				});
			} else if (subMenu.getText() == "�s�폜") {
				subMenu.setOnAction(ex -> {
					editAreaData.remove(index);
				});
			}
			// else if (subMenu.getText() == "�s�R�s�[") {
			// subMenu.setOnAction(ex -> {
			// Clipboard clipboard = Clipboard.getSystemClipboard();
			// ClipboardContent content = new ClipboardContent();
			// StringBuilder clipboardString = new StringBuilder();
			//
			//
			// EditArea area = editAreaData.get(index);
			//
			// clipboard.setContent(content);
			// });
			// } else if (subMenu.getText() == "�s�؂���") {
			// subMenu.setOnAction(ex -> {
			// //TODO
			//// EditArea area = editAreaData.get(index);
			//// editAreaData.remove(index);
			// });
			// }else if (subMenu.getText() == "�s�\��t��") {
			// subMenu.setOnAction(ex -> {
			// //TODO
			//// EditArea edit = new EditArea("", "", "", "", "");
			//// editAreaData.add(index, edit);
			// });
			// }

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

		newStage.setTitle("���ڑI��");

		TextField text1 = new TextField();
		TextField text2 = new TextField();

		Button btn = new Button();
		btn.setText("�o�^");
		btn.setOnAction(e -> {
			String str = "�uA�v���uB�v�ɐݒ肷��".replace("A", text1.getText()).replace("B", text2.getText());
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

		vbox.getChildren().addAll(text1, text2, btn);

		newStage.setScene(new Scene(vbox));
		newStage.show();

		return "";
	}

	private String openDialog2(Stage stage, int index) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("���ڑI��");

		TextField text1 = new TextField();
		TextField text2 = new TextField();

		Button btn = new Button();
		btn.setText("�o�^");
		btn.setOnAction(e -> {

			ObservableList<EditArea> ifLogicData = FXCollections.observableArrayList(
					new EditArea("IF", "IF", "", "", text1.getText(), ""), new EditArea("IF", "", "", "", "", ""),
					new EditArea("IF", "", "", "", "", ""), new EditArea("IF", "ELSE", "", "", "", ""),
					new EditArea("IF", "", "", "", "", ""), new EditArea("IF", "", "", "", "", ""),
					new EditArea("IF", "END", "", "", "", ""));

			editAreaData.addAll(index, ifLogicData);
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

	private String openDialog3(Stage stage, int index) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("���ڑI��");

		TextField text1 = new TextField();
		TextField text2 = new TextField();

		Button btn = new Button();
		btn.setText("�o�^");
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

		vbox.getChildren().addAll(text1, text2, btn);

		newStage.setScene(new Scene(vbox));
		newStage.show();

		return "";
	}
	
	
	private String openDialog4(Stage stage, int index,String str) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(stage);

		newStage.setTitle("���ڑI��");

		TextField text1 = new TextField();
		TextField text2 = new TextField();

		Button btn = new Button();
		btn.setText("�o�^");
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
