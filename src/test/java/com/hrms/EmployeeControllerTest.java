package com.hrms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.controller.EmployeeController;
import com.hrms.entity.Employee;
import com.hrms.dto.EmployeeDetailsDTO;
import com.hrms.service.IEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    @Mock
    private IEmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetActiveEmployees() throws Exception {
        List<Employee> mockEmployees = Collections.singletonList(new Employee());
        when(employeeService.getActiveEmployees()).thenReturn(mockEmployees);

        mockMvc.perform(get("/api/employees/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());

        verify(employeeService, times(1)).getActiveEmployees();
    }

    @Test
    void testSearchActiveEmployees() throws Exception {
        IPage<Employee> mockPage = new Page<>();
        when(employeeService.getActiveEmployeesByCondition(any(), any(), any(), any(), any()))
                .thenReturn(mockPage);

        mockMvc.perform(get("/api/employees/active/search")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists());

        verify(employeeService, times(1))
                .getActiveEmployeesByCondition(any(), any(), any(), any(), any());
    }

    @Test
    void testAddEmployee() throws Exception {
        Employee mockEmployee = new Employee();
        when(employeeService.createEmployeeAndInitialize(any())).thenReturn(true);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Employee\",\"isActive\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("新增员工成功，已初始化相关信息"));

        verify(employeeService, times(1)).createEmployeeAndInitialize(any());
    }

    @Test
    void testMarkEmployeeAsInactive() throws Exception {
        when(employeeService.markEmployeeAsInactive(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("员工已标记为离职"));

        verify(employeeService, times(1)).markEmployeeAsInactive(1L);
    }

    @Test
    void testGetEmployeeDetails() throws Exception {
        EmployeeDetailsDTO mockDetails = new EmployeeDetailsDTO();
        when(employeeService.getEmployeeDetailsById(1L)).thenReturn(mockDetails);

        mockMvc.perform(get("/api/employees/details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists());

        verify(employeeService, times(1)).getEmployeeDetailsById(1L);
    }
}