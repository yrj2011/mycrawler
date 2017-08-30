package com.itmuch.cloud.study.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.study.WalleResponse;
import com.itmuch.cloud.study.WalleStatus;
import com.itmuch.cloud.study.domain.TaskDto;
import com.itmuch.cloud.study.domain.User;
import com.itmuch.cloud.study.repository.UserRepository;

/**
 * 作用：
 * ① 测试服务实例的相关内容
 * ② 为后来的服务做提供
 * @author yrj
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private UserRepository userRepository;

  /**
   * 通过stationID查询任务信息
   * @param stationID
   * @return task信息
   */
  @GetMapping("/stations/{stationID}/tasks")
  public WalleResponse getStationTasks(@PathVariable Long stationID,HttpServletResponse resp) {
	  WalleResponse response = new WalleResponse();
	  try {
		 List<TaskDto>  tasks= new ArrayList<>();
	    /**
	     * 
	     *  业务代码
	     * 
	     */
	    
	       if(tasks == null || tasks.size() < 0) {
	        // 任务不存在
	    	      response.setStatus(WalleStatus.TASKNOTEXIST);
	      }else {
	    	        TaskDto task = new TaskDto();
	    	        task.setTaskID(1);
	    	        task.setState("COMPLETE");
	    	        tasks.add(task);
	    	        response.setResponse(tasks);  
	    	 // 请求成功
	    	        response.setStatus(WalleStatus.SUCCESS);
	    }
	   }catch(Exception e) {
		   // 请求异常
		    response.setStatus(WalleStatus.FAIL);
	   }
      return response;
  }
  
  /**
   * 通过id查询用户信息
   * @param id
   * @return user信息
   */
  @GetMapping("/{id}")
  @ResponseBody
  public User get(@PathVariable Long id,HttpServletResponse resp) {
    User findOne = this.userRepository.findOne(id);
    resp.setStatus(HttpStatus.BAD_REQUEST.value());
   
    return findOne;
  }
  
  /**
   * 查询所有用户信息
   * @return
   */
  @GetMapping() 
  @ResponseBody
  public List<User> getUsers() {
	  User user = new User();
	  user.setUsername(user.getUsername()+"-add");
	  User u2 = new User();
	  List<User> users = new ArrayList<User>();
	  users.add(u2);
	  users.add(user);
      return users;
  }
  
  /**
   * 添加用户信息
   * @param user
   * @return
   */
  @PostMapping()
  public User add(@RequestBody User user) {
	  user.setUsername(user.getUsername()+"-add");
      return user;
  }
  
  /**
   * 通过ID删除用户
   * @param id
   * @return
   */
  @DeleteMapping(value = "/{id}")
  public User delete(@PathVariable Long id) {
	User u2 = new User();
      return u2;
  }
  
  /**
   * 根据id修改用户所有属性
   * @param user
   * @return
   */
  @PutMapping(value = "/{id}")
  public User update(@RequestBody User user) {
	User u2 = new User();
      return u2;
  }
 
  /**
   *  根据id修改用户部分属性
   * @param user
   * @return
   */
  @PatchMapping()
  public User updatePatch(@RequestBody User user) {
		User u2 = new User();
	      return u2;
 }
  /**
   * 本地服务实例的信息
   * @return
   */
  @GetMapping("/instance-info")
  public ServiceInstance showInfo() {
    ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
    return localServiceInstance;
  }
}
