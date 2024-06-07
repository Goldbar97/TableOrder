package kang.tableorder.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kang.tableorder.service.RestaurantService;
import kang.tableorder.service.TablesService;
import kang.tableorder.service.UserService;
import kang.tableorder.setup.TablesControllerSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TablesControllerTest {

  // Test Setup Start

  TablesControllerSetup setup = new TablesControllerSetup();

  String token = "";

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserService userService;
  @Autowired
  private RestaurantService restaurantService;
  @Autowired
  private TablesService tablesService;

  @BeforeEach
  public void setUp() {

    userService.signUp(setup.signUpRequest);

    token = userService.signIn(setup.signInRequest).getToken();

    UserDetails userDetails = userService.loadUserByUsername(setup.email);

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
            userDetails.getAuthorities()));
  }

  // Test Setup End

  @Test
  void createSuccess() throws Exception {
    // given
    // when
    // then
    mockMvc.perform(post("/restaurants/tables")
            .header("Authorization", String.format("Bearer %s", token))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(setup.createTablesRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(setup.createTablesRequest.getNumber()))
        .andExpect(jsonPath("$.tabletMacId").value(setup.createTablesRequest.getTabletMacId()));
  }

  @Test
  void readListSuccess() throws Exception {
    // given
    tablesService.createTables(setup.email, setup.createTablesRequest);

    // when
    // then
    mockMvc.perform(get("/restaurants/tables")
            .header("Authorization", String.format("Bearer %s", token))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].number").value(setup.createTablesRequest.getNumber()))
        .andExpect(jsonPath("$[0].tabletMacId").value(setup.createTablesRequest.getTabletMacId()));
  }

  @Test
  void readElementSuccess() throws Exception {
    // given
    tablesService.createTables(setup.email, setup.createTablesRequest);

    // when
    // then
    mockMvc.perform(get("/restaurants/tables/1")
            .header("Authorization", String.format("Bearer %s", token))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(setup.createTablesRequest.getNumber()))
        .andExpect(jsonPath("$.tabletMacId").value(setup.createTablesRequest.getTabletMacId()));
  }

  @Test
  void updateSuccess() throws Exception {
    // given
    tablesService.createTables(setup.email, setup.createTablesRequest);

    // when
    // then
    mockMvc.perform(put("/restaurants/tables/1")
            .header("Authorization", String.format("Bearer %s", token))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(setup.updateTablesRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(setup.updateTablesRequest.getNumber()))
        .andExpect(jsonPath("$.tabletMacId").value(setup.updateTablesRequest.getTabletMacId()));
  }

  @Test
  void deleteSuccess() throws Exception {
    // given
    tablesService.createTables(setup.email, setup.createTablesRequest);

    // when
    // then
    mockMvc.perform(delete("/restaurants/tables/1")
            .header("Authorization", String.format("Bearer %s", token))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("삭제되었습니다."));
  }
}