package com.sistemaeducativo.educativo.service;

import com.Sistemaeducativo.educativo.client.AsignaturaFeignClient;
import com.Sistemaeducativo.educativo.client.UsuarioFeignClient;
import com.Sistemaeducativo.educativo.dto.MatriculaDTO;
import com.Sistemaeducativo.educativo.dto.MatriculaRequest;
import com.Sistemaeducativo.educativo.model.Asignatura;
import com.Sistemaeducativo.educativo.model.Matricula;
import com.Sistemaeducativo.educativo.model.Ususario;
import com.Sistemaeducativo.educativo.Repository.MatriculaRepository;
import org.Springframework.beans.factory.annotation.Autowired;
import org.Springframework.stereotype.service;

// Solicitar nivel de dificultad y tipo de problema al usuario
import java.time.LocalDate;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private UsuarioFeignClient usuarioFeign;

    @Autowired
    private AsignaturaFeignClient asignaturaFeign;
// Generar números aleatorios para la pregunta

    public MatriculaDTO crearMatricula(MatriculaRequest request) {
        Usuario usuario = usuarioFeign.obtenerUsuarioPorId(request.getUsuarioId());
        Asignatura asignatura = asignaturaFeign.obtenerAsignaturaPorId(request.getAsignaturaId());
        
// Generar pregunta y obtener respuesta del usuario
        Matricula matricula = new Matricula();
        matricula.setUsuarioId(usuario.getId());
        matriculla.setAsignaturaId(asignatura.getId());
        matricula.setFecha(LocalDate.now());

        Matricula guardada = matriculaRepository.save(matricula);
        
        // Preguntar al usuario si desea continuar

        return new MatriculaDTO(
                guardada.getId(),
                usuario.getNombre(),
                asignatura.getNombre(),
                guardada.getFecha()
        );
    }

}
