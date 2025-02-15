package io.github.dunwu.tool.bean;

import io.github.dunwu.tool.bean.support.BeanCopier;
import io.github.dunwu.tool.bean.support.BeanOptions;
import io.github.dunwu.tool.bean.support.NamingStrategy;
import io.github.dunwu.tool.bean.support.ValueProvider;
import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.convert.Convert;
import io.github.dunwu.tool.date.DatePattern;
import io.github.dunwu.tool.date.DateUtil;
import io.github.dunwu.tool.lang.Editor;
import io.github.dunwu.tool.lang.Filter;
import io.github.dunwu.tool.map.CaseInsensitiveMap;
import io.github.dunwu.tool.map.MapUtil;
import io.github.dunwu.tool.util.*;

import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Bean工具类
 *
 * <p>
 * 把一个拥有对属性进行set和get方法的类，我们就可以称之为JavaBean。
 *
 * @author Looly
 * @author Zhang Peng
 * @since 3.1.2
 */
public class BeanUtil {

    private BeanUtil() {}

    // ------------------------------------------------------------------------------ copyProperties

    /**
     * 复制Bean对象属性<br> 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source      源Bean对象
     * @param target      目标Bean对象
     * @param beanOptions 拷贝选项，见 {@link BeanOptions}
     */
    public static void copyProperties(Object source, Object target, BeanOptions beanOptions) {
        if (null == beanOptions) {
            beanOptions = BeanOptions.defaultBeanOptions();
        }
        BeanCopier.create(source, target, beanOptions).copy();
    }

    /**
     * 复制Bean对象属性<br>
     *
     * @param source     源Bean对象
     * @param target     目标Bean对象
     * @param ignoreCase 是否忽略大小写
     */
    public static void copyProperties(Object source, Object target, boolean ignoreCase) {
        BeanCopier.create(source, target, BeanOptions.create().setIgnoreCase(ignoreCase)).copy();
    }

    /**
     * 复制Bean对象属性
     *
     * @param source 源Bean对象
     * @param target 目标Bean对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, BeanOptions.create());
    }

    /**
     * 复制Bean对象属性<br> 限制类用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类
     *
     * @param source           源Bean对象
     * @param target           目标Bean对象
     * @param ignoreProperties 不拷贝的的属性列表
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        copyProperties(source, target, BeanOptions.create().setIgnoreProperties(ignoreProperties));
    }

    // ------------------------------------------------------------------------------ 针对 field 的操作

    /**
     * 创建动态Bean
     *
     * @param bean 普通Bean或Map
     * @return {@link DynaBean}
     * @since 3.0.7
     */
    public static DynaBean createDynaBean(Object bean) {
        return new DynaBean(bean);
    }

    /**
     * 获取{@link BeanDesc} Bean描述信息
     *
     * @param clazz Bean类
     * @return {@link BeanDesc}
     * @since 3.1.2
     */
    public static BeanDesc getBeanDesc(Class<?> clazz) {
        BeanDesc beanDesc = BeanDescCache.INSTANCE.getBeanDesc(clazz);
        if (null == beanDesc) {
            beanDesc = new BeanDesc(clazz);
            BeanDescCache.INSTANCE.putBeanDesc(clazz, beanDesc);
        }
        return beanDesc;
    }

    /**
     * 获得字段值，通过反射直接获得字段值，并不调用getXXX方法<br> 对象同样支持Map类型，fieldNameOrIndex即为key
     *
     * @param bean             Bean对象
     * @param fieldNameOrIndex 字段名或序号，序号支持负数
     * @return 字段值
     */
    public static Object getFieldValue(Object bean, String fieldNameOrIndex) {
        if (null == bean || null == fieldNameOrIndex) {
            return null;
        }

        if (bean instanceof Map) {
            return ((Map<?, ?>) bean).get(fieldNameOrIndex);
        } else if (bean instanceof Collection) {
            return CollectionUtil.get((Collection<?>) bean, Integer.parseInt(fieldNameOrIndex));
        } else if (ArrayUtil.isArray(bean)) {
            return ArrayUtil.get(bean, Integer.parseInt(fieldNameOrIndex));
        } else {// 普通Bean对象
            return ReflectUtil.getFieldValue(bean, fieldNameOrIndex);
        }
    }

    /**
     * 设置字段值，，通过反射设置字段值，并不调用setXXX方法<br> 对象同样支持Map类型，fieldNameOrIndex即为key
     *
     * @param bean             Bean
     * @param fieldNameOrIndex 字段名或序号，序号支持负数
     * @param value            值
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void setFieldValue(Object bean, String fieldNameOrIndex, Object value) {
        if (bean instanceof Map) {
            ((Map) bean).put(fieldNameOrIndex, value);
        } else if (bean instanceof List) {
            CollectionUtil.setOrAppend((List) bean, Convert.toInt(fieldNameOrIndex), value);
        } else if (ArrayUtil.isArray(bean)) {
            ArrayUtil.setOrAppend(bean, Convert.toInt(fieldNameOrIndex), value);
        } else {
            // 普通Bean对象
            ReflectUtil.setFieldValue(bean, fieldNameOrIndex, value);
        }
    }

    /**
     * 解析Bean中的属性值
     *
     * @param <T>        属性值类型
     * @param bean       Bean对象，支持Map、List、Collection、Array
     * @param expression 表达式，例如：person.friend[5].name
     * @return Bean属性值
     * @see BeanPath#get(Object)
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object bean, String expression) {
        return (T) BeanPath.create(expression).get(bean);
    }

    /**
     * 解析Bean中的属性值
     *
     * @param bean       Bean对象，支持Map、List、Collection、Array
     * @param expression 表达式，例如：person.friend[5].name
     * @param value      属性值
     * @see BeanPath#get(Object)
     * @since 4.0.6
     */
    public static void setProperty(Object bean, String expression, Object value) {
        BeanPath.create(expression).set(bean, value);
    }

    /**
     * 获得Bean类属性描述，大小写敏感
     *
     * @param clazz     Bean类
     * @param fieldName 字段名
     * @return PropertyDescriptor
     * @throws BeanException 获取属性异常
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, final String fieldName)
        throws BeanException {
        return getPropertyDescriptor(clazz, fieldName, false);
    }

    /**
     * 获得Bean类属性描述
     *
     * @param clazz      Bean类
     * @param fieldName  字段名
     * @param ignoreCase 是否忽略大小写
     * @return PropertyDescriptor
     * @throws BeanException 获取属性异常
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, final String fieldName, boolean ignoreCase)
        throws BeanException {
        final Map<String, PropertyDescriptor> map = getPropertyDescriptorMap(clazz, ignoreCase);
        return (null == map) ? null : map.get(fieldName);
    }

    /**
     * 获得Bean字段描述数组
     *
     * @param clazz Bean类
     * @return 字段描述数组
     * @throws BeanException 获取属性异常
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeanException {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new BeanException(e);
        }
        return ArrayUtil.filter(beanInfo.getPropertyDescriptors(), (Filter<PropertyDescriptor>) t -> {
            // 过滤掉getClass方法
            return false == "class".equals(t.getName());
        });
    }

    /**
     * 获得字段名和字段描述Map，获得的结果会缓存在 {@link BeanInfoCache}中
     *
     * @param clazz      Bean类
     * @param ignoreCase 是否忽略大小写
     * @return 字段名和字段描述Map
     * @throws BeanException 获取属性异常
     */
    public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> clazz, boolean ignoreCase)
        throws BeanException {
        Map<String, PropertyDescriptor> map = BeanInfoCache.INSTANCE.getPropertyDescriptorMap(clazz, ignoreCase);
        if (null == map) {
            map = internalGetPropertyDescriptorMap(clazz, ignoreCase);
            BeanInfoCache.INSTANCE.putPropertyDescriptorMap(clazz, map, ignoreCase);
        }
        return map;
    }

    /**
     * 获得字段名和字段描述Map。内部使用，直接获取Bean类的PropertyDescriptor
     *
     * @param clazz      Bean类
     * @param ignoreCase 是否忽略大小写
     * @return 字段名和字段描述Map
     * @throws BeanException 获取属性异常
     */
    private static Map<String, PropertyDescriptor> internalGetPropertyDescriptorMap(Class<?> clazz, boolean ignoreCase)
        throws BeanException {
        final PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        final Map<String, PropertyDescriptor> map = ignoreCase ? new CaseInsensitiveMap<>(propertyDescriptors.length, 1)
            : new HashMap<>((int) (propertyDescriptors.length), 1);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            map.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return map;
    }

    /**
     * 把Bean里面的String属性做trim操作。此方法直接对传入的Bean做修改。
     * <p>
     * 通常bean直接用来绑定页面的input，用户的输入可能首尾存在空格，通常保存数据库前需要把首尾空格去掉
     *
     * @param <T>          Bean类型
     * @param bean         Bean对象
     * @param ignoreFields 不需要trim的Field名称列表（不区分大小写）
     * @return 处理后的Bean对象
     */
    public static <T> T trimStrFields(T bean, String... ignoreFields) {
        if (bean == null) {
            return bean;
        }

        final Field[] fields = ReflectUtil.getFields(bean.getClass());
        for (Field field : fields) {
            if (ignoreFields != null && ArrayUtil.containsIgnoreCase(ignoreFields, field.getName())) {
                // 不处理忽略的Fields
                continue;
            }
            if (String.class.equals(field.getType())) {
                // 只有String的Field才处理
                final String val = (String) ReflectUtil.getFieldValue(bean, field);
                if (null != val) {
                    final String trimVal = StringUtil.trim(val);
                    if (!val.equals(trimVal)) {
                        // Field Value不为null，且首尾有空格才处理
                        ReflectUtil.setFieldValue(bean, field, trimVal);
                    }
                }
            }
        }

        return bean;
    }

    // --------------------------------------------------------------------------------------------- mapToBean

    /**
     * 判断是否为Bean对象<br> 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     * @since 4.2.2
     */
    public static boolean hasGetter(Class<?> clazz) {
        if (ClassUtil.isNormalClass(clazz)) {
            final Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 0) {
                    if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有Setter方法<br> 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     * @since 4.2.2
     */
    public static boolean hasSetter(Class<?> clazz) {
        if (ClassUtil.isNormalClass(clazz)) {
            final Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    // 检测包含标准的setXXX方法即视为标准的JavaBean
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断Bean是否包含值为<code>null</code>的属性<br> 对象本身为<code>null</code>也返回true
     *
     * @param bean             Bean对象
     * @param ignoreFiledNames 忽略检查的字段名
     * @return 是否包含值为<code>null</code>的属性，<code>true</code> - 包含 / <code>false</code> - 不包含
     * @since 4.1.10
     */
    public static boolean hasNullField(Object bean, String... ignoreFiledNames) {
        if (null == bean) {
            return true;
        }
        for (Field field : ReflectUtil.getFields(bean.getClass())) {
            if ((false == ArrayUtil.contains(ignoreFiledNames, field.getName()))//
                && null == ReflectUtil.getFieldValue(bean, field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为Bean对象<br> 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     * @see #hasSetter(Class)
     */
    public static boolean isBean(Class<?> clazz) {
        return hasSetter(clazz);
    }

    /**
     * 给定的Bean的类名是否匹配指定类名字符串<br> 如果isSimple为{@code false}，则只匹配类名而忽略包名，例如：cn.hutool.TestEntity只匹配TestEntity<br>
     * 如果isSimple为{@code true}，则匹配包括包名的全类名，例如：cn.hutool.TestEntity匹配cn.hutool.TestEntity
     *
     * @param bean          Bean
     * @param beanClassName Bean的类名
     * @param isSimple      是否只匹配类名而忽略包名，true表示忽略包名
     * @return 是否匹配
     * @since 4.0.6
     */
    public static boolean isMatchName(Object bean, String beanClassName, boolean isSimple) {
        return ClassUtil.getClassName(bean, isSimple)
            .equals(isSimple ? StringUtil.upperFirst(beanClassName) : beanClassName);
    }

    /**
     * 判断Bean是否为非空对象，非空对象表示本身不为<code>null</code>或者含有非<code>null</code>属性的对象
     *
     * @param bean             Bean对象
     * @param ignoreFiledNames 忽略检查的字段名
     * @return 是否为空，<code>true</code> - 空 / <code>false</code> - 非空
     * @since 5.0.7
     */
    public static boolean isNotEmpty(Object bean, String... ignoreFiledNames) {
        return !isEmpty(bean, ignoreFiledNames);
    }

    /**
     * 判断Bean是否为空对象，空对象表示本身为<code>null</code>或者所有属性都为<code>null</code>
     *
     * @param bean             Bean对象
     * @param ignoreFiledNames 忽略检查的字段名
     * @return 是否为空，<code>true</code> - 空 / <code>false</code> - 非空
     * @since 4.1.10
     */
    public static boolean isEmpty(Object bean, String... ignoreFiledNames) {
        if (null != bean) {
            for (Field field : ReflectUtil.getFields(bean.getClass())) {
                if ((!ArrayUtil.contains(ignoreFiledNames, field.getName()))
                    && null != ReflectUtil.getFieldValue(bean, field)) {
                    return false;
                }
            }
        }
        return true;
    }

    // ------------------------------------------------------------------------------ fillBean

    /**
     * 填充Bean的核心方法
     *
     * @param <T>           Bean类型
     * @param bean          Bean
     * @param valueProvider 值提供者
     * @param beanOptions   拷贝选项，见 {@link BeanOptions}
     * @return Bean
     */
    public static <T> T fillBean(T bean, ValueProvider<String> valueProvider, BeanOptions beanOptions) {
        if (null == valueProvider) {
            return bean;
        }

        return BeanCopier.create(valueProvider, bean, beanOptions).copy();
    }

    /**
     * 使用Map填充Bean对象
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isIgnoreError) {
        return fillBeanWithMap(map, bean, false, isIgnoreError);
    }

    /**
     * 使用Map填充Bean对象，可配置将下划线转换为驼峰
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isToCamelCase 是否将下划线模式转换为驼峰模式
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isToCamelCase, boolean isIgnoreError) {
        return fillBeanWithMap(map, bean, isToCamelCase, BeanOptions.create().setIgnoreError(isIgnoreError));
    }

    /**
     * 使用Map填充Bean对象
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isToCamelCase 是否将Map中的下划线风格key转换为驼峰风格
     * @param beanOptions   属性复制选项 {@link BeanOptions}
     * @return Bean
     * @since 3.3.1
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, boolean isToCamelCase, BeanOptions beanOptions) {
        if (MapUtil.isEmpty(map)) {
            return bean;
        }
        if (isToCamelCase) {
            map = MapUtil.toCamelCaseMap(map);
        }
        return BeanCopier.create(map, bean, beanOptions).copy();
    }

    /**
     * 使用Map填充Bean对象
     *
     * @param <T>         Bean类型
     * @param map         Map
     * @param bean        Bean
     * @param beanOptions 属性复制选项 {@link BeanOptions}
     * @return Bean
     */
    public static <T> T fillBeanWithMap(Map<?, ?> map, T bean, BeanOptions beanOptions) {
        return fillBeanWithMap(map, bean, false, beanOptions);
    }

    /**
     * 使用Map填充Bean对象，忽略大小写
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param bean          Bean
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T fillBeanWithMapIgnoreCase(Map<?, ?> map, T bean, boolean isIgnoreError) {
        BeanOptions beanOptions = BeanOptions.create().setIgnoreCase(true).setIgnoreError(isIgnoreError);
        return fillBeanWithMap(map, bean, beanOptions);
    }

    // ------------------------------------------------------------------------------ toBean

    /**
     * ServletRequest 参数转Bean
     *
     * @param <T>           Bean类型
     * @param beanClass     Bean Class
     * @param valueProvider 值提供者
     * @param beanOptions   拷贝选项，见 {@link BeanOptions}
     * @return Bean
     */
    public static <T> T toBean(Class<T> beanClass, ValueProvider<String> valueProvider, BeanOptions beanOptions) {
        return fillBean(ReflectUtil.newInstance(beanClass), valueProvider, beanOptions);
    }

    /**
     * 对象或Map转Bean
     *
     * @param <T>    转换的Bean类型
     * @param source Bean对象或Map
     * @param clazz  目标的Bean类型
     * @return Bean对象
     * @since 4.1.20
     */
    public static <T> T toBean(Object source, Class<T> clazz) {
        final T target = ReflectUtil.newInstance(clazz);
        copyProperties(source, target);
        return target;
    }

    /**
     * Map转换为Bean对象
     *
     * @param <T>           Bean类型
     * @param map           {@link Map}
     * @param beanClass     Bean Class
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T toBean(Map<?, ?> map, Class<T> beanClass, boolean isIgnoreError) {
        return fillBeanWithMap(map, ReflectUtil.newInstance(beanClass), isIgnoreError);
    }

    /**
     * Map转换为Bean对象
     *
     * @param <T>         Bean类型
     * @param map         {@link Map}
     * @param beanClass   Bean Class
     * @param beanOptions 转Bean选项
     * @return Bean
     */
    public static <T> T toBean(Map<?, ?> map, Class<T> beanClass, BeanOptions beanOptions) {
        return fillBeanWithMap(map, ReflectUtil.newInstance(beanClass), beanOptions);
    }

    /**
     * Map转换为Bean对象<br> 忽略大小写
     *
     * @param <T>           Bean类型
     * @param map           Map
     * @param beanClass     Bean Class
     * @param isIgnoreError 是否忽略注入错误
     * @return Bean
     */
    public static <T> T toBeanIgnoreCase(Map<?, ?> map, Class<T> beanClass, boolean isIgnoreError) {
        return fillBeanWithMapIgnoreCase(map, ReflectUtil.newInstance(beanClass), isIgnoreError);
    }

    // ------------------------------------------------------------------------------ toMap

    /**
     * 对象转 Map，不忽略值为空的字段
     *
     * @param bean bean对象
     * @return Map
     */
    public static Map<String, Object> toMap(Object bean) {
        return toMap(bean, NamingStrategy.DEFAULT, false);
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param namingStrategy  命名策略 {@link NamingStrategy}
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> toMap(Object bean, NamingStrategy namingStrategy, boolean ignoreNullValue) {
        return toMap(bean, new LinkedHashMap<>(), namingStrategy, ignoreNullValue);
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param targetMap       目标的Map
     * @param namingStrategy  命名策略 {@link NamingStrategy}
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     * @since 3.2.3
     */
    public static Map<String, Object> toMap(Object bean, Map<String, Object> targetMap,
        final NamingStrategy namingStrategy, boolean ignoreNullValue) {
        if (bean == null) {
            return null;
        }

        return toMap(bean, targetMap, ignoreNullValue,
            key -> formatKeyword(key, namingStrategy));
    }

    /**
     * 对象转Map<br> 通过实现{@link Editor} 可以自定义字段值，如果这个Editor返回null则忽略这个字段，以便实现：
     *
     * <pre>
     * 1. 字段筛选，可以去除不需要的字段
     * 2. 字段变换，例如实现驼峰转下划线
     * 3. 自定义字段前缀或后缀等等
     * </pre>
     *
     * @param bean            bean对象
     * @param targetMap       目标的Map
     * @param ignoreNullValue 是否忽略值为空的字段
     * @param keyEditor       属性字段（Map的key）编辑器，用于筛选、编辑key
     * @return Map
     * @since 4.0.5
     */
    public static Map<String, Object> toMap(Object bean, Map<String, Object> targetMap, boolean ignoreNullValue,
        Editor<String> keyEditor) {
        if (bean == null) {
            return null;
        }

        final Collection<BeanDesc.PropDesc> props = BeanUtil.getBeanDesc(bean.getClass()).getProps();

        String key;
        Method getter;
        Object value;
        for (BeanDesc.PropDesc prop : props) {
            key = prop.getFieldName();
            // 过滤class属性
            // 得到property对应的getter方法
            getter = prop.getGetter();
            if (null != getter) {
                // 只读取有getter方法的属性
                try {
                    value = getter.invoke(bean);
                } catch (Exception ignore) {
                    continue;
                }
                if (!ignoreNullValue || (null != value && !value.equals(bean))) {
                    key = keyEditor.edit(key);
                    if (null != key) {
                        targetMap.put(key, value);
                    }
                }
            }
        }
        return targetMap;
    }

    /**
     * 查找类型转换器 {@link PropertyEditor}
     *
     * @param type 需要转换的目标类型
     * @return {@link PropertyEditor}
     */
    public static PropertyEditor toPropertyEditor(Class<?> type) {
        return PropertyEditorManager.findEditor(type);
    }

    // ------------------------------------------------------------------------------ 格式化处理 Bean 字段

    /**
     * 将指定的 keyword 按照 {@link NamingStrategy} 记性格式化处理
     *
     * @param keyword        需要格式化的 keyword
     * @param namingStrategy 重新命名的策略
     * @return 格式化后的字符串
     */
    public static String formatKeyword(final String keyword, final NamingStrategy namingStrategy) {
        if (StringUtil.isBlank(keyword)) {
            return keyword;
        }

        String formatStr = keyword;
        switch (namingStrategy) {
            case CAMEL:
                formatStr = StringUtil.toCamelCase(formatStr);
                break;
            case LOWER_UNDERLINE:
                formatStr = StringUtil.toUnderlineCase(formatStr).toLowerCase();
                break;
            case UPPER_UNDERLINE:
                formatStr = StringUtil.toUnderlineCase(formatStr).toUpperCase();
                break;
            case LOWER_DASHED:
                formatStr = StringUtil.toSymbolCase(formatStr, CharUtil.DASHED).toLowerCase();
                break;
            case UPPER_DASHED:
                formatStr = StringUtil.toSymbolCase(formatStr, CharUtil.DASHED).toUpperCase();
                break;
            case DEFAULT:
            default:
                break;
        }
        return formatStr;
    }

    /**
     * 将 Bean 所属类的所有 key 格式化为一个字符串
     *
     * @param clazz 需要格式化为字符串的 Bean
     * @return 已经按照配置格式化后的 Bean 成员名称列表
     */
    public static List<String> formatKeys(Class<?> clazz, NamingStrategy namingStrategy) {
        Field[] fields = ReflectUtil.getFields(clazz);
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = field.getName();
            fieldName = formatKeyword(fieldName, namingStrategy);
            fieldNames.add(fieldName);
        }
        return fieldNames;
    }

    /**
     * 将类的所有 field 用指定字符拼接为一个字符串
     *
     * @param clazz
     * @param separator
     * @return
     */
    public static String joinKeys(Class<?> clazz, String separator) {
        List<String> keys = formatKeys(clazz, NamingStrategy.DEFAULT);
        return StringUtil.join(separator, keys.toArray());
    }

    /**
     * 将 Bean 的所有 value 用指定字符拼接为一个字符串
     *
     * @param bean      需要格式化为字符串的 Bean
     * @param separator 分隔符
     * @param <T>       Bean 类型
     * @return 字符串
     */
    public static <T> String joinValues(T bean, String separator) {
        Map<String, Object> map = BeanUtil.toMap(bean);
        if (MapUtil.isEmpty(map)) {
            return StringUtil.EMPTY;
        }

        List<Object> list = new ArrayList<>();
        Collection<Object> collection = map.values();
        for (Object obj : collection) {

            if (obj instanceof Date) {
                Date date = (Date) obj;
                String dateStr = DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
                list.add(dateStr);
            } else {
                list.add(obj);
            }
        }
        return StringUtil.join(separator, list.toArray());
    }

}
