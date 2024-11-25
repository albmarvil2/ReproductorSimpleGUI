package reproductor;

import java.util.List;

import javax.swing.JSlider;

import sonido.Sonido;

public interface Reproductor {

	public abstract void siguiente();

	public abstract void anterior();

	public abstract void start();

	public abstract void stop();

	public abstract void pause();

	public abstract void restart();

	public abstract boolean isPlaying();

	public abstract boolean isPaused();

	public abstract boolean isStopped();

	public abstract void setPosicion(Long posicion);

	public abstract void creaListaReproduccion();

	public abstract Integer getIndice();

	public abstract void setIndice(Integer indice);

	public abstract boolean isRandom();

	public abstract void setRandom(boolean random);

	public abstract boolean isLoop();

	public abstract void setLoop(boolean loop);

	public abstract Reproduccion getReproduccion();
	
	public abstract JSlider getSlider();

	public abstract void setSlider(JSlider slider);

	public abstract List<Sonido> getSonidos();

}