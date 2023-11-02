package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CuestionarioController implements Initializable {

	@FXML
	private Label minDec;

	@FXML
	private Label minUnidad;

	@FXML
	private Label segUnidad;

	@FXML
	private Label segDec;

	@FXML
	private Button btnEnviar;

	private int min;

	private int seg;

	BooleanProperty activo = new SimpleBooleanProperty(true);

	@FXML
	void enviar(ActionEvent event) {
		Node n = (Node) event.getSource();
		Stage stage = (Stage) n.getScene().getWindow();
		stage.close();
	}

	public void setTiempo(int tiempo) throws Exception {
		if (tiempo < 1 || tiempo > 99) {
			throw new Exception("Numero fuera de rango 1-99");

		} else {
			this.min = tiempo;
			this.seg = 1;
			minDec.setText(min % 10 + "");
			minUnidad.setText((min / 10) % 10 + "");
			segDec.setText(seg % 10 + "");
			segUnidad.setText((seg / 10) % 10 + "");

			Timer timer = new Timer();

			TimerTask segundos = new TimerTask() {

				@Override
				public void run() {
					seg--;
					if (seg < 0) {
						seg = 59;
						min--;
					}
					/**
					 * It will run it after the void run of the timetask so that the threads don't
					 * run at the same time and show an error
					 */
					Platform.runLater(() -> {
						obtenerNumero(seg, segDec, segUnidad);
						obtenerNumero(min, minDec, minUnidad);
					});
					if (min == 0 && seg == 0) {
						Platform.runLater(() -> {
							obtenerNumero(seg, segDec, segUnidad);
							obtenerNumero(min, minDec, minUnidad);
						});
						activo.set(false);
						timer.cancel();
					}
				}
			};

			timer.scheduleAtFixedRate(segundos, 0, 1000);
		}
	}

	private void obtenerNumero(int valorTiempo, Label lblDec, Label lblUnidad) {

		int uni = valorTiempo % 10;

		int dec = (valorTiempo / 10) % 10;

		lblUnidad.setText(uni + "");
		lblDec.setText(dec + "");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.setTiempo(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * Cuando se cambie el valor de Boolean activo se desabilitará el botón de
		 * enviar
		 */
		activo.addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
				btnEnviar.setDisable(true);
			}
		});
	}

}
