
package com.taskmanager.taskmanager_api.controller;

import com.taskmanager.taskmanager_api.model.Status;
import com.taskmanager.taskmanager_api.model.Tarefa;
import com.taskmanager.taskmanager_api.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TarefaController.class)
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarefaService tarefaService;

    @Test
    void deveListarTarefas() throws Exception {
        when(tarefaService.listarTarefas()).thenReturn(List.of(
                new Tarefa(1L, "Estudar Java", Status.PENDENTE),
                new Tarefa(2L, "Estudar Spring", Status.EM_ANDAMENTO)
        ));

        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Estudar Java"))
                .andExpect(jsonPath("$[1].status").value("EM_ANDAMENTO"));
    }


    @Test
    void deveFiltrarTarefasPorStatus() throws Exception {
        when(tarefaService.listarTarefasPorStatus(Status.PENDENTE))
                .thenReturn(List.of(
                        new Tarefa(1L, "Estudar Java", Status.PENDENTE)
                ));

        mockMvc.perform(get("/tarefas")
                        .param("status", "PENDENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDENTE"));

        verify(tarefaService).listarTarefasPorStatus(Status.PENDENTE);
    }


    @Test
    void deveCriarTarefa() throws Exception {
        when(tarefaService.criarTarefa(
                "Estudar Java",
                Status.PENDENTE
        )).thenReturn(
                new Tarefa(1L, "Estudar Java", Status.PENDENTE)
        );

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/tarefas")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "titulo": "Estudar Java",
                                "status": "PENDENTE"
                            }
                            """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Estudar Java"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }


    @Test
    void naoDeveCriarTarefaComTituloVazio() throws Exception {
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .post("/tarefas")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "titulo": "   ",
                                "status": "PENDENTE"
                            }
                            """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagens.titulo").exists());

        org.mockito.Mockito.verify(
                tarefaService,
                org.mockito.Mockito.never()
        ).criarTarefa(
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.any()
        );
    }


    @Test
    void naoDeveEditarTarefaComTituloVazio() throws Exception {
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/tarefas/1")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "titulo": "   ",
                                "status": "PENDENTE"
                            }
                            """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagens.titulo").exists());

        org.mockito.Mockito.verify(
                tarefaService,
                org.mockito.Mockito.never()
        ).editarTarefa(
                org.mockito.ArgumentMatchers.anyLong(),
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.any()
        );
    }


    @Test
    void deveRemoverTarefa() throws Exception {
        when(tarefaService.removerTarefa(1L)).thenReturn(true);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/tarefas/1")
                )
                .andExpect(status().isNoContent());

        org.mockito.Mockito.verify(tarefaService).removerTarefa(1L);
    }


    @Test
    void naoDeveRemoverTarefaInexistente() throws Exception {
        when(tarefaService.removerTarefa(99L)).thenReturn(false);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/tarefas/99")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro")
                        .value("Tarefa não encontrada"))
                .andExpect(jsonPath("$.mensagem")
                        .value("Não existe uma tarefa com o ID informado."));

        org.mockito.Mockito.verify(tarefaService).removerTarefa(99L);
    }


    @Test
    void naoDeveEditarTarefaInexistente() throws Exception {
        when(tarefaService.editarTarefa(
                99L,
                "Estudar Spring Boot",
                Status.PENDENTE
        )).thenReturn(false);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/tarefas/99")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "titulo": "Estudar Spring Boot",
                                "status": "PENDENTE"
                            }
                            """)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro")
                        .value("Tarefa não encontrada"))
                .andExpect(jsonPath("$.mensagem")
                        .value("Não existe uma tarefa com o ID informado."));

        org.mockito.Mockito.verify(tarefaService).editarTarefa(
                99L,
                "Estudar Spring Boot",
                Status.PENDENTE
        );
    }


}
