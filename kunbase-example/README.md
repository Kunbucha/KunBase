# KunBase

<details>
<summary><b>Table of content</b></summary>

## Table of content

- [What is KunBase](#what-is-KunBase)
  - [Current status](#current-status)
  - [Quickstart](#quickstart)
  - [Features](#features)
  - [Modules](#modules)
  - [Key abstractions](#key-abstractions)
- [Using KunBase](#using-KunBase)
  - [Maven](#maven)
  - [Gradle](#gradle)
  - [Building from Source](#building-from-source)
  - [With KunBase-archetype](#with-KunBase-archetype)

</details>

---

## What is KunBase?

KunBase is a lightweight flexible development framework for complex business architecture.

Originated from business，serve business！

一套轻量级业务中台开发框架，以 DDD 思想为本，致力于业务资产的可沉淀可传承，全方位解决复杂业务场景的扩展问题，实现**中台核心要素**，赋能中台建设。

融合了前中台复杂生态协作方法论，充分考虑组织架构、技术债、学习门槛、可演进性、运维成本和风险而开发的，解决业务开发痛点，是中台架构的顶层设计和完整解决方案。

从业务中来，到业务中去！

### Current status

Used for several complex critical central platform projects in production environment.

多个复杂的中台核心项目生产环境下使用。

### Features

- Based on DDD, but beyond DDD
- 14 key business abstractions cover most complex business scenarios
- Full layered extensibility
- Empowers InnerSource
- Provide maven archetype that generates a KunBase integrated project
- Total solutions oriented
- Above all, KunBase is simple enough

核心特性：

- 以 DDD 架构思想为本，面向复杂业务场景架构设计
  - 通过代码框架提供足够的约束和指导，让 DDD 不再仅停留在思想层面
  - 只引入弱依赖的 `IDomainModel`，弱化其他概念，降低 DDD 上手门槛
  - DDD 分层架构上增加一层`spec layer`，解决前中台协同问题
- 14 个核心业务抽象(常用 9 个)，勾勒出业务中台骨架
  - 中台架构的顶层设计
  - less is more，以不变应万变
  - 研发专注于填空式开发，只需解决局部问题
- 全方位解决业务的不确定性
  - 业务逻辑、流程、逻辑模型、数据模型的扩展、多态体系
  - 框架本身支持再次扩展，便于被集成
  - 抽象出独立的业务扩展包，框架底层通过`ClassLoader`机制进行业务隔离，支持热更新
  - 平台容器包、平台业务包与业务扩展包：分离
- 支撑中台战略的复杂生态协作
  - 前台、中台解耦
  - 业务隔离，不同前台间业务隔离，前台和中台隔离
  - 支持稳态、敏态双速应用
  - InnerSource，生态合作协同机制
- 完整的解决方案
  - 业务能力演化，业务测试，最佳实践，架构持续防腐，重构的导流验证，绞杀者落地方案等
  - 提供一套完整的 Demo 工程
  - 演示5分钟搭建一个仓储中台 WMS，手把手真实场景教学
- KunBase 框架，始终保持简单性

### Modules

```
KunBase
   ├── kunbase-spec    - Specification of KunBase
   ├── kunbase-runtime - Runtime implementation
   ├── kunbase-plugin  - Plugin jar hot reloading mechanism
   ├── kunbase-unit    - Extra unit test facilities
   ├── kunbase-enforce - Enforce expected evolvement of the business architecture
   └── kunbase-test    - Fully covered unit test cases
```

## Using KunBase

已推送至[Maven 中央库](https://search.maven.org/search?q=g:io.github.kunbase)，可直接引入。

### Maven

```xml
<dependency>
    <groupId>io.github.kunbase</groupId>
    <artifactId>kunbase-runtime</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    ...
    compile 'io.github.KunBase:KunBase-runtime:1.1.0'
}
```

### Building from Source

```bash
git clone hhttps://github.com/Kunbucha/KunBase.git work
cd work/
mvn install
```

### With KunBase-archetype

```bash
mvn archetype:generate                          \
    -DarchetypeGroupId=io.github.KunBase        \
    -DarchetypeArtifactId=KunBase-archetype     \
    -DarchetypeVersion=1.1.0                    \
    -DgroupId=com.foo -DartifactId=demo         \
    -Dpackage=com.foo -Dversion=1.0.0-SNAPSHOT  \
    -B
```

## Licensing

KunBase is licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0).
