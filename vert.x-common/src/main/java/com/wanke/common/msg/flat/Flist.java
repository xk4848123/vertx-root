// automatically generated by the FlatBuffers compiler, do not modify

package com.wanke.common.msg.flat;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Flist extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static Flist getRootAsFlist(ByteBuffer _bb) { return getRootAsFlist(_bb, new Flist()); }
  public static Flist getRootAsFlist(ByteBuffer _bb, Flist obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Flist __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public Fmap datas(int j) { return datas(new Fmap(), j); }
  public Fmap datas(Fmap obj, int j) { int o = __offset(4); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int datasLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public Fmap.Vector datasVector() { return datasVector(new Fmap.Vector()); }
  public Fmap.Vector datasVector(Fmap.Vector obj) { int o = __offset(4); return o != 0 ? obj.__assign(__vector(o), 4, bb) : null; }

  public static int createFlist(FlatBufferBuilder builder,
      int datasOffset) {
    builder.startTable(1);
    Flist.addDatas(builder, datasOffset);
    return Flist.endFlist(builder);
  }

  public static void startFlist(FlatBufferBuilder builder) { builder.startTable(1); }
  public static void addDatas(FlatBufferBuilder builder, int datasOffset) { builder.addOffset(0, datasOffset, 0); }
  public static int createDatasVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startDatasVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endFlist(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishFlistBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedFlistBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Flist get(int j) { return get(new Flist(), j); }
    public Flist get(Flist obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}
