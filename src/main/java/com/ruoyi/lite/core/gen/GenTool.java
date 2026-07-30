package com.ruoyi.lite.core.gen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 *
 * @author Fooyao
 * @date 2026/7/22
 */
public class GenTool {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://rm-bp1b14f25d915bmr00o.mysql.rds.aliyuncs.com:3306/ruoyi-lite?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8", "root", "Fooyao106423@")
                .globalConfig(builder -> {
                    builder.author("Fooyao") // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .outputDir("D:\\apps\\projects\\gacha-hub\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.gacha.hub") // 设置父包名
                                .entity("entity")
                                .mapper("mapper")
                                .service("service")
                                .serviceImpl("service.impl")
                                // .moduleName("system") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\apps\\projects\\gacha-hub\\src\\main\\resources\\mapper")) // 设置mapperXml生成路径
                )
                // .strategyConfig(builder ->
                //         builder
                //                 .entityBuilder()
                //                 .enableLombok()
                // )
                .strategyConfig(builder ->
                        builder
                                // 👉 单表：只生成这一张，带前缀就加 addTablePrefix("t_")
                                .addInclude("user_account")
                                // Entity 覆盖
                                .entityBuilder()
                                .enableSerialAnnotation()
                                .enableLombok()
                                .enableFileOverride()
                                // Mapper + Xml 覆盖
                                .mapperBuilder()
                                .enableFileOverride()
                                // Service + Impl 覆盖
                                .serviceBuilder()
                                .enableFileOverride()
                                // Controller 覆盖（不需要可删）
                                .controllerBuilder()
                                .enableRestStyle()
                                .enableFileOverride()

                )

                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
