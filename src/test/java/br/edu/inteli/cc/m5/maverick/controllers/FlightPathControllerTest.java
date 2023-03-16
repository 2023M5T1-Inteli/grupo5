//package br.edu.inteli.cc.m5.maverick.controllers;
//
//import br.edu.inteli.cc.m5.maverick.exceptions.ResourceNotFoundException;
//import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
//import br.edu.inteli.cc.m5.maverick.repositories.FlightNodeRepository;
//import br.edu.inteli.cc.m5.maverick.services.DTEDDatabaseService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(FlightPathController.class)
//class FlightPathControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DTEDDatabaseService dtedDatabaseService;
//
//    @MockBean
//    private FlightNodeRepository flightNodeRepository;
//
//    private List<FlightNodeEntity> nodes;
//
//    @BeforeEach
//    public void setup() {
//        FlightNodeEntity node1 = new FlightNodeEntity(1L, 2.0, 3.0, 4.0);
//        FlightNodeEntity node2 = new FlightNodeEntity(2L, 5.0, 6.0, 7.0);
//        nodes = Arrays.asList(node1, node2);
//    }
//
//    @Test
//    void populateNodes_shouldCallDtedDatabaseService() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/flight-path/nodes"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        verify(dtedDatabaseService, times(1)).readPointsFromDataset();
//    }
//
//    @Test
//    void getNodes_shouldReturnListOfNodes() throws Exception {
//        when(flightNodeRepository.findAll()).thenReturn(nodes);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/flight-path/nodes")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"latitude\":2.0,\"longitude\":3.0,\"elevation\":4.0},{\"id\":2,\"latitude\":5.0,\"longitude\":6.0,\"elevation\":7.0}]"));
//    }
//
//    @Test
//    void updateNode_shouldUpdateSpecifiedNode() throws Exception {
//        FlightNodeEntity nodeToUpdate = new FlightNodeEntity(1L, 2.0, 3.0, 4.0);
//        FlightNodeEntity updatedNode = new FlightNodeEntity(1L, 2.0, 5.0, 3.0);
//
//        when(flightNodeRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(nodeToUpdate));
//        when(flightNodeRepository.save(any())).thenReturn(updatedNode);
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/flight-path/nodes/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\":1,\"latitude\":2.0,\"longitude\":5.0, \"elevation\":3.0}"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"latitude\":2.0,\"longitude\":5.0,\"elevation\":3.0}"));
//    }
//
//    @Test
//    void updateNode_shouldThrowExceptionWhenNodeNotFound() throws Exception {
//        when(flightNodeRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/flight-path/nodes/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\":1,\"latitude\":2.0,\"longitude\":5.0}"))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
//
//    @Test
//    void deleteNode_shouldDeleteSpecifiedNode() throws Exception {
//        FlightNodeEntity nodeToDelete = new FlightNodeEntity(1L, 2.0, 3.0, 4.0);
//
//        when(flightNodeRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(nodeToDelete));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/flight-path/nodes/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        verify(flightNodeRepository, times(1)).delete(nodeToDelete);
//    }
//
//    @Test
//    void deleteNode_shouldThrowExceptionWhenNodeNotFound() throws Exception {
//        when(flightNodeRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/flight-path/nodes/1"))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
//
//    @Test
//    void apiWelcome_shouldReturnWelcomeMessage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/flight-path/api"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Welcome to AEL flight path management"));
//    }
//}