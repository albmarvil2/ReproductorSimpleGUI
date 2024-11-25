package reproductor;

import javax.swing.JSlider;

import sonido.Sonido;
/**
 * Clase destinada a los controles básicos de una Reproducción,
 * start,stop y pause. Extiende de Thread para el tratamiento de
 * hilos. En el cuerpo del método run() es donde se lanza la reproduccion
 * @author Alberto Martínez Villarán
 *
 */
public class Reproduccion extends Thread{
	
	private EstadoReproduccion estado;
	private Sonido sonido;
	private Long posicion;
	private Reproductor reproductor;
	private JSlider slider;
	/**
	 * Constructor de una Reproducción a partir de otra reproduccion
	 * ya existente
	 * @param reproduccion
	 */
	public Reproduccion(Reproduccion reproduccion){
		this.sonido=reproduccion.getSonido();
		this.posicion = reproduccion.getPosicion();
		this.estado = EstadoReproduccion.PLAYING;
//		System.out.println("constructor0-->"+estado);
		this.slider = reproduccion.slider;
		this.reproductor = reproduccion.reproductor;
	}
	/**
	 * Constructor a partir de un sonido y el reproductor que contiene a esta reproduccion.
	 * @param sonido
	 * @param reproductor
	 */
	public Reproduccion(Sonido sonido, Reproductor reproductor,JSlider slider){
		this.sonido =sonido;
		this.estado = EstadoReproduccion.PLAYING;
//		System.out.println("constructor1-->"+estado);
		this.posicion = (long)0;
		this.slider = slider;
		this.slider.setMaximum((int) this.sonido.getClip().getMicrosecondLength());
		this.reproductor = reproductor;
	}

	public EstadoReproduccion getEstadoReproduccion(){
		return this.estado;
	}
	
	public void setEstadoReproduccion(EstadoReproduccion estado){
		this.estado = estado;
//		System.out.println("setEstadoReproduccion-->"+this.estado);
	}
	public Sonido getSonido() {
		return sonido;
	}
	public Long getPosicion(){
		return this.posicion;
	}
	public void setPosicion(Long pos){
		this.posicion = pos;
	}
	/**
	 * Para la reproducción y cambia el estado de la misma a ENDED
	 */
	public void stopReproduccion(){
		this.sonido.getClip().stop();
		this.estado=EstadoReproduccion.ENDED;
//		System.out.println("stopReproduccion-->"+estado);
	}
	/**
	 * Para la reproduccion guardando previamente la posicion en la que esta,
	 * cambia el estado a PAUSED. Facilitando así su relanzamiento.
	 */
	public void pauseReproduccion(){
		this.posicion = this.sonido.getClip().getMicrosecondPosition();
		this.sonido.getClip().stop();
		this.estado=EstadoReproduccion.PAUSED;
//		System.out.println("pauseReproduccion-->"+estado);
	}
	
	/**
	 * Inicia la reproducción del audio en un hilo distinto desde el que se ha llamado.
	 * La reproduccion se hace a través del Clip del sonido y en la ultima posición guardada.
	 * Mientras esta reproduciendo no hay problemas, pero cuando se acaba de lanzar se hace una 
	 * espera activa de 1 ms, que es lo que se tarda de media en cargar el Clip entero.
	 * 
	 * Cuando acaba la reproducción del Clip de audio la reproducción llama al reproductor y lanza la siguiente
	 * cancion que este en cola en el reproductor.
	 */
	public void run(){
		this.sonido.abrir();
		this.sonido.getClip().setMicrosecondPosition(posicion);
		this.sonido.getClip().start();// Inicia el clip de audio
		while(this.estado == EstadoReproduccion.PLAYING){
			while (!this.sonido.getClip().isRunning()) {// espera activa de 1 ms que es lo que tarda en responder
				// el metodo start
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.slider.setValue((int) this.sonido.getClip().getMicrosecondPosition());
			Long diff = this.sonido.getClip().getMicrosecondLength()-this.sonido.getClip().getMicrosecondPosition();
			if(diff<100){//Cuando acabe el clip,pasa al siguiente. Haciendo esto evitamos que si pasamos manualmente en la mitad del clip no se creen hilos paralelos.
				stopReproduccion();
				reproductor.siguiente();
			}
	
		}		
	}
}
