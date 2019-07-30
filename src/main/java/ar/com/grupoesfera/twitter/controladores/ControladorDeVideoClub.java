package ar.com.grupoesfera.twitter.controladores;

import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.grupoesfera.twitter.modelo.bc.Pelicula;
import ar.com.grupoesfera.twitter.modelo.bc.VideoClub;

@Controller
public class ControladorDeVideoClub {
	LinkedList <Pelicula> peliculas = new LinkedList <>();
	
	public  LinkedList <Pelicula> obtenerPeliculas() {
		
		return  (LinkedList<Pelicula>) VideoClub.instancia().obtenerTodas();
	}
	
	@RequestMapping("videoclub/listar-peliculas")
	public ModelAndView mostrar() {
		ModelAndView model = new ModelAndView("video-club/listado");
		peliculas = obtenerPeliculas();
		model.addObject("peliculas", peliculas);
		return model;
	}
	
	@RequestMapping("video-club/buscar-por-nombre")
	public ModelAndView obtenerPeliculasPorNombre(@RequestParam ("nombre") String peliculaBuscada) {
		ModelAndView model = new ModelAndView();
		peliculas = buscarPorNombre(peliculaBuscada);
		model.addObject("peliculas", peliculas);
		model.setViewName("video-club/listado");
		return model;
		
	}
	
	private LinkedList<Pelicula> buscarPorNombre(String peliculaBuscada) {
		LinkedList<Pelicula> peliculasBusqueda= new LinkedList<Pelicula>();
		for (Pelicula pelicula: peliculas) {
			if(pelicula.getNombre().contains(peliculaBuscada)) {
				peliculasBusqueda.add(pelicula);
			}
		}
		return peliculasBusqueda;
	}


	/*@RequestMapping("video-club/pelicula-con-mayor-rating")
	public ModelAndView peliculaConMayorRating() {
		ModelAndView model = new ModelAndView("video-club/pelicula-mas-votada");
		model.addObject("pelicula",)
	}*/
}
