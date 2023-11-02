package controllers;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CuestionarioController {

    @FXML
    private Label minDec;

    @FXML
    private Label minUnidad;

    @FXML
    private Label segUnidad;

    @FXML
    private Label segDec;
    
    private int min;

	private int seg;
	
	BooleanProperty activo = new SimpleBooleanProperty(true);

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
					obtenerNumero(seg, minDec, minUnidad);
					if (seg == 0) {
						seg = 60;
						min--;
//						obtenerNumero(seg, minDec, minUnidad);
						obtenerNumero(min, segDec, segUnidad);
					}
					if (min == 0 && seg == 1) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						obtenerNumero(0, minDec, minUnidad);
						obtenerNumero(0, segDec, segUnidad);
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
    	
}
