package reproductor;

import gui.GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JSlider;

import sonido.FactoriaSonido;
import sonido.Sonido;


public class ReproductorImpl implements Reproductor{
	
	private Reproduccion rep;
	private FactoriaSonido fs;
	private List<Sonido> sonidos;
	private Integer indice;
	private boolean random;
	private boolean loop;
	
	private JSlider slider;
	
	public ReproductorImpl(JSlider slider){
		this.rep = null;
		this.fs = new FactoriaSonido();
		this.sonidos = new ArrayList<Sonido>();
		this.indice = 0;
		this.random = false;
		this.loop = true;
		
		this.slider = slider;
	}
	
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#siguiente()
	 */
	@Override
	public void siguiente(){
		rep.stopReproduccion();
		if(random){
			Double rand = Math.random();
			for(int i=0; i<sonidos.size(); i++){
				if(new Double(i)/new Double(sonidos.size())>= rand){
					indice = i;
					start();
					GUI.actualizaInfo();
					break;
				}
			}
		}else if(loop && !random){
			if(indice==sonidos.size()-1){
				indice = 0;				
			}else{
				indice = indice+1;
			}
			start();
			GUI.actualizaInfo();
		}else if(!random && !loop){
			if(indice == sonidos.size()-1){
				rep.stopReproduccion();
				indice = 0;
			}else{
				indice = indice+1;
				start();
				GUI.actualizaInfo();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#anterior()
	 */
	@Override
	public void anterior(){
		rep.stopReproduccion();
		if(random){
			Double rand = Math.random();
			for(int i=0; i<sonidos.size(); i++){
				if(new Double(i)/new Double(sonidos.size())>= rand){
					indice = i;
					start();
					GUI.actualizaInfo();
					break;
				}
			}
		}else if(loop && !random){
			if(indice==0){
				indice = sonidos.size()-1;				
			}else{
				indice = indice-1;
			}
			start();
			GUI.actualizaInfo();
		}else if(!random && !loop){
			if(indice == 0){
				rep.stopReproduccion();
			}else{
				indice = indice-1;
				start();
				GUI.actualizaInfo();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#start()
	 */
	@Override
	public void start(){
		this.rep = new Reproduccion(sonidos.get(indice), this,slider);
		this.rep.start();
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#stop()
	 */
	@Override
	public void stop(){
		this.rep.stopReproduccion();
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#pause()
	 */
	@Override
	public void pause(){
		this.rep.pauseReproduccion();
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#restart()
	 */
	@Override
	public void restart(){
		this.rep = new Reproduccion(rep);
		this.rep.start();
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#isPlaying()
	 */
	@Override
	public boolean isPlaying(){
		return (this.rep != null) &&(this.rep.getEstadoReproduccion() == EstadoReproduccion.PLAYING);
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#isPaused()
	 */
	@Override
	public boolean isPaused(){
		return (this.rep != null) &&(this.rep.getEstadoReproduccion() == EstadoReproduccion.PAUSED);
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#isStopped()
	 */
	@Override
	public boolean isStopped(){
		return (this.rep != null) &&(this.rep.getEstadoReproduccion() == EstadoReproduccion.ENDED);
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#setPosicion(java.lang.Long)
	 */
	@Override
	public void setPosicion(Long posicion){
		this.rep.pauseReproduccion();
		this.rep.setPosicion(posicion);
	}
	
	/* (non-Javadoc)
	 * @see reproductor.Reproductor#creaListaReproduccion()
	 */
	@Override
	public void creaListaReproduccion(){
		this.sonidos = this.fs.createSonidos();
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#getIndice()
	 */
	@Override
	public Integer getIndice() {
		return indice;
	}
	
	public Reproduccion getReproduccion(){
		return rep;
	}

	/* (non-Javadoc)
	 * @see reproductor.Reproductor#setIndice(java.lang.Integer)
	 */
	@Override
	public void setIndice(Integer indice) {
		this.indice = indice;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#isRandom()
	 */
	@Override
	public boolean isRandom() {
		return random;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#setRandom(boolean)
	 */
	@Override
	public void setRandom(boolean random) {
		this.random = random;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#isLoop()
	 */
	@Override
	public boolean isLoop() {
		return loop;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#setLoop(boolean)
	 */
	@Override
	public void setLoop(boolean loop) {
		this.loop = loop;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#getSlider()
	 */
	@Override
	public JSlider getSlider() {
		return slider;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#setSlider(javax.swing.JSlider)
	 */
	@Override
	public void setSlider(JSlider slider) {
		this.slider = slider;
	}


	/* (non-Javadoc)
	 * @see reproductor.Reproductor#getSonidos()
	 */
	@Override
	public List<Sonido> getSonidos() {
		return sonidos;
	}
}