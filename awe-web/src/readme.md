#参数匹配

###现有资源

#### 所有参数名称
1. 匹配后参数 其中每个重载方法都将进行匹配，参数匹配不成功值null, 基本类型参数不能为null
2. 重载方法匹配结果可能所有参数都是null
> ```
> paramsTypes[] 可能匹配结果[null, null, null...]
> realParamsNames 
> 通过HttpservletRequest中获得的真实 paramsName
> method paramsTypes[] [String.class, int.class, Integer.class]
> method paramsNames[] [name, age, sex] [notNull, notNull, null]
> method paramsNames[] [name, age] [notNull, notNull]
> request paramsName[] [name, age]
> ajax params [name]
> ajax params [name, age]
> 结果
> params[]{notNull, null}
> ```

#### 如何获得唯一请求资源


# 大文件上传
1. webuploader
2. RandomAccessFile 随机流，任意访问文件（位置）
3. MappedByteBuffer FileChannel提供了map方法把文件映射到虚拟内存，MappedByteBuffer使用虚拟内存，分配的内存大小不受JVM限制