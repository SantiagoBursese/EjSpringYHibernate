package ar.com.grupoesfera.twitter.modelo.bc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.com.grupoesfera.twitter.modelo.bc.Pelicula.Genero;

public class VideoClub {
	
	private static VideoClub instancia = new VideoClub();
	
	private Set<Long> idsUsados;
	private List<Pelicula> peliculas;
	
	private VideoClub() {
		
		idsUsados = new HashSet<>();
		peliculas = new LinkedList<Pelicula>();
		
		Pelicula endgame = Pelicula.nueva()
								   .conNombre("Vengadores: Endgame")
								   .conDuracion("3h 1min")
								   .conGenero(Genero.ACCION)
								   .conAnio("2019")
								   .conRatingDeUsuarios(475291L)
								   .conPrecion(new BigDecimal(150))
								   .conId(generarId());
		
		Pelicula infinityWars = Pelicula.nueva()
										.conNombre("Vengadores: Infinity War")
										.conDuracion("2h 29min")
										.conGenero(Genero.ACCION)
										.conAnio("2018")
										.conRatingDeUsuarios(684366L)
										.conPrecion(new BigDecimal(150))
										.conId(generarId());
		
		Pelicula it = Pelicula.nueva()
							  .conNombre("It")
							  .conDuracion("2h 15min")
							  .conGenero(Genero.TERROR)
							  .conAnio("2017")
							  .conRatingDeUsuarios(365014L)
							  .conPrecion(new BigDecimal(120))
							  .conId(generarId());
		
		Pelicula elConjuro = Pelicula.nueva()
									 .conNombre("El conjuro")
									 .conDuracion("1h 52min")
									 .conGenero(Genero.TERROR)
									 .conAnio("2013")
									 .conRatingDeUsuarios(400661L)
									 .conPrecion(new BigDecimal(99.99))
									 .conId(generarId());
		
		Pelicula locoPorMary = Pelicula.nueva()
									   .conNombre("Loco por Mary")
									   .conDuracion("1h 59min")
									   .conGenero(Genero.COMEDIA)
									   .conAnio("1998")
									   .conRatingDeUsuarios(275902L)
									   .conPrecion(new BigDecimal(25))
									   .conId(generarId());		
		peliculas.add(endgame);
		peliculas.add(infinityWars);
		peliculas.add(it);
		peliculas.add(elConjuro);
		peliculas.add(locoPorMary);
	}
	
	public List<Pelicula> obtenerTodas() {
		return peliculas;
	}
	
	public Pelicula obtenerPorId(Long peliculaId) {
		return peliculas.stream()
						.filter(pelicula -> pelicula.getId().equals(peliculaId))
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("No se encontró la pelicula con id:" + peliculaId));
	}
	
	public static VideoClub instancia() {
		return instancia;
	}
	
	private Long generarId() {
		
		Long ultimo = idsUsados.stream()
				 			   .reduce((primero, segundo) -> segundo)
				 			   .orElse(0L);
		
		Long idNuevo = ultimo + 1;
		
		idsUsados.add(idNuevo);
		
		return idNuevo;
	}
}
