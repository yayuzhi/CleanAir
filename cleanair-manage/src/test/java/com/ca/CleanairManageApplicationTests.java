package com.ca;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CleanairManageApplicationTests {

    /**
     * 部署流程实例
     */
    @Test
    public void testDeployment() {
        //1、创建processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3、使用RepositoryService进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/Aftermarket.bpmn")
                .addClasspathResource("process/Aftermarket.png")
                .name("请假流程")
                .deploy();
        System.out.println("部署的流程实例 category=" + deployment.getCategory() + ";id=" + deployment.getId() +
                ";name=" + deployment.getName() + ";TenantId=" + deployment.getTenantId() + ";deloymentTime=" + deployment.getDeploymentTime());
    }


}
