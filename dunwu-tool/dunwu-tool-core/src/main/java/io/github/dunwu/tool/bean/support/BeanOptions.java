package io.github.dunwu.tool.bean.support;

import io.github.dunwu.tool.map.MapUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * JavaBean 选项
 * <p>
 * 包括：
 * <p>
 * 1、限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
 * <p>
 * 2、是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
 * <p>
 * 3、忽略的属性列表，设置一个属性列表，不拷贝这些属性值
 *
 * @author Looly
 * @author Zhang Peng
 */
public class BeanOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否忽略字段大小写
     */
    protected boolean ignoreCase;

    /**
     * 是否忽略字段注入错误
     */
    protected boolean ignoreError;

    /**
     * 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     */
    protected boolean ignoreNullValue;

    /**
     * 属性命名策略
     */
    protected NamingStrategy namingStrategy;

    /**
     * 限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     */
    protected Class<?> editable;

    /**
     * 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     */
    protected String[] ignoreProperties;

    /**
     * 拷贝属性的字段映射，用于不同的属性之前拷贝做对应表用
     */
    protected Map<String, String> fieldMapping;

    /**
     * 构造拷贝选项
     */
    public BeanOptions() {
    }

    /**
     * 构造拷贝选项
     *
     * @param editable         限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
     * @param ignoreNullValue  是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     * @param ignoreProperties 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     */
    public BeanOptions(Class<?> editable, boolean ignoreNullValue, String... ignoreProperties) {
        this.editable = editable;
        this.ignoreNullValue = ignoreNullValue;
        this.ignoreProperties = ignoreProperties;
    }

    /**
     * 创建拷贝选项
     *
     * @return 拷贝选项
     */
    public static BeanOptions defaultBeanOptions() {
        BeanOptions beanOptions = new BeanOptions();
        beanOptions.setIgnoreCase(true)
            .setIgnoreError(true)
            .setIgnoreNullValue(true);
        return beanOptions;
    }

    /**
     * 创建拷贝选项
     *
     * @return 拷贝选项
     */
    public static BeanOptions create() {
        return new BeanOptions();
    }

    /**
     * 创建拷贝选项
     *
     * @param editable         限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
     * @param ignoreNullValue  是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     * @param ignoreProperties 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
     * @return 拷贝选项
     */
    public static BeanOptions create(Class<?> editable, boolean ignoreNullValue, String... ignoreProperties) {
        return new BeanOptions(editable, ignoreNullValue, ignoreProperties);
    }

    /**
     * 设置是否忽略字段的大小写
     *
     * @param ignoreCase 是否忽略大小写
     * @return BeanOptions
     */
    public BeanOptions setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        return this;
    }

    /**
     * 设置是否忽略字段的注入错误
     *
     * @param ignoreError 是否忽略注入错误
     * @return BeanOptions
     */
    public BeanOptions setIgnoreError(boolean ignoreError) {
        this.ignoreError = ignoreError;
        return this;
    }

    /**
     * 设置是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     *
     * @param ignoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     * @return BeanOptions
     */
    public BeanOptions setIgnoreNullValue(boolean ignoreNullValue) {
        this.ignoreNullValue = ignoreNullValue;
        return this;
    }

    /**
     * 设置 Bean 属性的命名策略
     *
     * @param namingStrategy {@link NamingStrategy}
     * @return BeanOptions
     */
    public BeanOptions setNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
        return this;
    }

    /**
     * 设置限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性
     *
     * @param editable 限制的类或接口
     * @return BeanOptions
     */
    public BeanOptions setEditable(Class<?> editable) {
        this.editable = editable;
        return this;
    }

    /**
     * 设置忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     *
     * @param ignoreProperties 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     * @return BeanOptions
     */
    public BeanOptions setIgnoreProperties(String... ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
        return this;
    }

    /**
     * 设置拷贝属性的字段映射，用于不同的属性之前拷贝做对应表用
     *
     * @param fieldMapping 拷贝属性的字段映射，用于不同的属性之前拷贝做对应表用
     * @return BeanOptions
     */
    public BeanOptions setFieldMapping(Map<String, String> fieldMapping) {
        this.fieldMapping = fieldMapping;
        return this;
    }

    /**
     * 获取反转之后的映射
     *
     * @return 反转映射
     * @since 4.1.10
     */
    protected Map<String, String> getReversedMapping() {
        return (null != this.fieldMapping) ? MapUtil.reverse(this.fieldMapping) : null;
    }

}
