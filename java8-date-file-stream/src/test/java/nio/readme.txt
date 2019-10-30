**ByteBuffer** 是将数据移入和移出通道的唯一方法，我们只能创建一个独立的基本类型缓冲区，
或者使用 `as` 方法从 **ByteBuffer** 获得一个新缓冲区。也就是说，不能将基本类型缓冲区转换为 **ByteBuffer**。
但我们能够通过视图缓冲区将基本类型数据移动到 **ByteBuffer** 中或移出 **ByteBuffer**。