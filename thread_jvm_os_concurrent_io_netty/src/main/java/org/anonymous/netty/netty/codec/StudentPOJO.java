package org.anonymous.netty.netty.codec;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class StudentPOJO {
    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_Student_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_Student_fieldAccessorTable;
    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\rStudent.proto\"#\n\007Student\022\n\n\002id\030\001 \001(\005\022\014" +
                        "\n\004name\030\002 \001(\tB\rB\013StudentPOJOb\006proto3"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        }, assigner);
        internal_static_Student_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_Student_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_Student_descriptor,
                new String[]{"Id", "Name",});
    }

    private StudentPOJO() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    public interface StudentOrBuilder extends
            // @@protoc_insertion_point(interface_extends:Student)
            MessageOrBuilder {

        /**
         * <pre>
         * Student 类中有 一个属性 名字为 id 类型为int32(protobuf类型) 1表示属性序号，不是值
         * </pre>
         *
         * <code>int32 id = 1;</code>
         */
        int getId();

        /**
         * <code>string name = 2;</code>
         */
        String getName();

        /**
         * <code>string name = 2;</code>
         */
        ByteString getNameBytes();
    }

    /**
     * <pre>
     * protobuf 使用message 管理数据
     * </pre>
     * <p>
     * Protobuf type {@code Student}
     */
    public static final class Student extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:Student)
            StudentOrBuilder {
        public static final int ID_FIELD_NUMBER = 1;
        public static final int NAME_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0L;
        // @@protoc_insertion_point(class_scope:Student)
        private static final Student DEFAULT_INSTANCE;
        private static final Parser<Student> PARSER = new AbstractParser<Student>() {
            @Override
            public Student parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Student(input, extensionRegistry);
            }
        };

        static {
            DEFAULT_INSTANCE = new Student();
        }

        private int id_;
        private volatile Object name_;
        private byte memoizedIsInitialized = -1;

        // Use Student.newBuilder() to construct.
        private Student(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Student() {
            id_ = 0;
            name_ = "";
        }

        private Student(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8: {

                            id_ = input.readInt32();
                            break;
                        }
                        case 18: {
                            String s = input.readStringRequireUtf8();

                            name_ = s;
                            break;
                        }
                        default: {
                            if (!parseUnknownFieldProto3(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
            return StudentPOJO.internal_static_Student_descriptor;
        }

        public static Student parseFrom(ByteBuffer data)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Student parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Student parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Student parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Student parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Student parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Student parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Student parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Student parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Student parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Student parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Student parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Student prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public static Student getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Student> parser() {
            return PARSER;
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return StudentPOJO.internal_static_Student_fieldAccessorTable.ensureFieldAccessorsInitialized(Student.class, Builder.class);
        }

        /**
         * <pre>
         * Student 类中有 一个属性 名字为 id 类型为int32(protobuf类型) 1表示属性序号，不是值
         * </pre>
         *
         * <code>int32 id = 1;</code>
         */
        public int getId() {
            return id_;
        }

        /**
         * <code>string name = 2;</code>
         */
        public String getName() {
            Object ref = name_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                name_ = s;
                return s;
            }
        }

        /**
         * <code>string name = 2;</code>
         */
        public ByteString getNameBytes() {
            Object ref = name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                name_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output)
                throws java.io.IOException {
            if (id_ != 0) {
                output.writeInt32(1, id_);
            }
            if (!getNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, name_);
            }
            unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (id_ != 0) {
                size += CodedOutputStream.computeInt32Size(1, id_);
            }
            if (!getNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, name_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Student)) {
                return super.equals(obj);
            }
            Student other = (Student) obj;

            boolean result = true;
            result = result && (getId() == other.getId());
            result = result && getName().equals(other.getName());
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            hash = (37 * hash) + ID_FIELD_NUMBER;
            hash = (53 * hash) + getId();
            hash = (37 * hash) + NAME_FIELD_NUMBER;
            hash = (53 * hash) + getName().hashCode();
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        @Override
        public Parser<Student> getParserForType() {
            return PARSER;
        }

        @Override
        public Student getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        /**
         * <pre>
         * protobuf 使用message 管理数据
         * </pre>
         * <p>
         * Protobuf type {@code Student}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:Student)
                StudentOrBuilder {
            private int id_;
            private Object name_ = "";

            // Construct using StudentPOJO.Student.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return StudentPOJO.internal_static_Student_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return StudentPOJO.internal_static_Student_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                Student.class, Builder.class);
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                id_ = 0;

                name_ = "";

                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return StudentPOJO.internal_static_Student_descriptor;
            }

            @Override
            public Student getDefaultInstanceForType() {
                return Student.getDefaultInstance();
            }

            @Override
            public Student build() {
                Student result = buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Student buildPartial() {
                Student result = new Student(this);
                result.id_ = id_;
                result.name_ = name_;
                onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return super.clone();
            }

            @Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.setField(field, value);
            }

            @Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof Student) {
                    return mergeFrom((Student) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(Student other) {
                if (other == Student.getDefaultInstance()) return this;
                if (other.getId() != 0) {
                    setId(other.getId());
                }
                if (!other.getName().isEmpty()) {
                    name_ = other.name_;
                    onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                Student parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (Student) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            /**
             * <pre>
             * Student 类中有 一个属性 名字为 id 类型为int32(protobuf类型) 1表示属性序号，不是值
             * </pre>
             *
             * <code>int32 id = 1;</code>
             */
            public int getId() {
                return id_;
            }

            /**
             * <pre>
             * Student 类中有 一个属性 名字为 id 类型为int32(protobuf类型) 1表示属性序号，不是值
             * </pre>
             *
             * <code>int32 id = 1;</code>
             */
            public Builder setId(int value) {

                id_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * Student 类中有 一个属性 名字为 id 类型为int32(protobuf类型) 1表示属性序号，不是值
             * </pre>
             *
             * <code>int32 id = 1;</code>
             */
            public Builder clearId() {

                id_ = 0;
                onChanged();
                return this;
            }

            /**
             * <code>string name = 2;</code>
             */
            public String getName() {
                Object ref = name_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    name_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string name = 2;</code>
             */
            public Builder setName(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                name_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string name = 2;</code>
             */
            public com.google.protobuf.ByteString
            getNameBytes() {
                Object ref = name_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    name_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>string name = 2;</code>
             */
            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                AbstractMessageLite.checkByteStringIsUtf8(value);

                name_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string name = 2;</code>
             */
            public Builder clearName() {

                name_ = getDefaultInstance().getName();
                onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:Student)
        }

    }

    // @@protoc_insertion_point(outer_class_scope)
}