/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.runtime;

import org.cdf.ddd.runtime.registry.Container;
import org.cdf.ddd.runtime.registry.InternalIndexer;
import org.cdf.ddd.runtime.registry.StepDef;
import org.cdf.ddd.step.IDomainStep;
import org.cdf.ddd.model.BaseDomainAbility;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DDD框架的核心类，定位核心对象.
 */
public final class DDD {
    private DDD() {
    }

    /**
     * 获取业务容器单例.
     */
    @NotNull
    public static Container getContainer() {
        return Container.getInstance();
    }

    /**
     * 定位一个领域能力点实例.
     *
     * @param domainAbilityClazz 领域能力类型
     * @param <T>                领域能力类型
     * @return null if bug found：研发忘记使用注解DomainAbility了
     */
    @NotNull
    public static <T extends BaseDomainAbility> T findAbility(@NotNull Class<? extends T> domainAbilityClazz) {
        return InternalIndexer.findDomainAbility(domainAbilityClazz);
    }

    /**
     * 根据步骤编号定位领域活动步骤.
     *
     * @param activityCode 所属领域活动编号
     * @param stepCodeList 领域步骤编号列表
     * @param <Step>       领域步骤类型
     * @return 如果没找到，会返回数量为0的非空列表
     */
    @NotNull
    public static <Step extends IDomainStep> List<Step> findSteps(@NotNull String activityCode, @NotNull List<String> stepCodeList) {
        List<StepDef> stepDefs = InternalIndexer.findDomainSteps(activityCode, stepCodeList);
        List<Step> result = new ArrayList<>(stepDefs.size());
        for (StepDef stepDef : stepDefs) {
            result.add((Step) stepDef.getStepBean());
        }

        return result;
    }

    /**
     * 定位某一个领域步骤实例.
     *
     * @param activityCode 所属领域活动编号
     * @param stepCode     步骤编号
     * @param <Step>       领域步骤类型
     * @return 如果找不到，则返回null
     */
    public static <Step extends IDomainStep> Step getStep(@NotNull String activityCode, @NotNull String stepCode) {
        List<Step> steps = findSteps(activityCode, Arrays.asList(stepCode));
        if (steps.size() == 1) {
            return steps.get(0);
        }

        return null;
    }

}
