; ModuleID = '/home/lasse/IdeaProjects/Dat3M/output/transfer_example.ll'
source_filename = "/home/lasse/IdeaProjects/Dat3M/database_applications/transfer_example/transfer_example.c"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

%union.pthread_attr_t = type { i64, [48 x i8] }

@x = dso_local global i32 50, align 4, !dbg !0
@y = dso_local global i32 50, align 4, !dbg !18
@.str = private unnamed_addr constant [12 x i8] c"!(a+b==100)\00", align 1
@.str.1 = private unnamed_addr constant [89 x i8] c"/home/lasse/IdeaProjects/Dat3M/database_applications/transfer_example/transfer_example.c\00", align 1
@__PRETTY_FUNCTION__.read = private unnamed_addr constant [19 x i8] c"void *read(void *)\00", align 1

; Function Attrs: noinline nounwind uwtable
define dso_local i8* @transaction(i8* noundef %0) #0 !dbg !31 {
  call void @llvm.dbg.value(metadata i8* %0, metadata !35, metadata !DIExpression()), !dbg !36
  fence acquire, !dbg !37
  store volatile i32 100, i32* @y, align 4, !dbg !38
  store volatile i32 0, i32* @x, align 4, !dbg !39
  fence release, !dbg !40
  ret i8* undef, !dbg !41
}

; Function Attrs: nofree nosync nounwind readnone speculatable willreturn
declare void @llvm.dbg.declare(metadata, metadata, metadata) #1

; Function Attrs: noinline nounwind uwtable
define dso_local i8* @read(i8* noundef %0) #0 !dbg !42 {
  call void @llvm.dbg.value(metadata i8* %0, metadata !43, metadata !DIExpression()), !dbg !44
  fence acquire, !dbg !45
  %2 = load volatile i32, i32* @x, align 4, !dbg !46
  call void @llvm.dbg.value(metadata i32 %2, metadata !47, metadata !DIExpression()), !dbg !44
  %3 = load volatile i32, i32* @y, align 4, !dbg !48
  call void @llvm.dbg.value(metadata i32 %3, metadata !49, metadata !DIExpression()), !dbg !44
  fence release, !dbg !50
  %4 = add nsw i32 %2, %3, !dbg !51
  %5 = icmp eq i32 %4, 100, !dbg !51
  br i1 %5, label %6, label %7, !dbg !54

6:                                                ; preds = %1
  call void @__assert_fail(i8* noundef getelementptr inbounds ([12 x i8], [12 x i8]* @.str, i64 0, i64 0), i8* noundef getelementptr inbounds ([89 x i8], [89 x i8]* @.str.1, i64 0, i64 0), i32 noundef 29, i8* noundef getelementptr inbounds ([19 x i8], [19 x i8]* @__PRETTY_FUNCTION__.read, i64 0, i64 0)) #4, !dbg !51
  unreachable, !dbg !51

7:                                                ; preds = %1
  ret i8* undef, !dbg !55
}

; Function Attrs: noreturn nounwind
declare void @__assert_fail(i8* noundef, i8* noundef, i32 noundef, i8* noundef) #2

; Function Attrs: noinline nounwind uwtable
define dso_local i32 @main() #0 !dbg !56 {
  %1 = alloca i64, align 8
  %2 = call i32 @pthread_create(i64* noundef undef, %union.pthread_attr_t* noundef null, i8* (i8*)* noundef @transaction, i8* noundef null) #5, !dbg !59
  call void @llvm.dbg.declare(metadata i64* %1, metadata !60, metadata !DIExpression()), !dbg !64
  %3 = call i32 @pthread_create(i64* noundef %1, %union.pthread_attr_t* noundef null, i8* (i8*)* noundef @read, i8* noundef null) #5, !dbg !65
  ret i32 0, !dbg !66
}

; Function Attrs: nounwind
declare i32 @pthread_create(i64* noundef, %union.pthread_attr_t* noundef, i8* (i8*)* noundef, i8* noundef) #3

; Function Attrs: nofree nosync nounwind readnone speculatable willreturn
declare void @llvm.dbg.value(metadata, metadata, metadata) #1

attributes #0 = { noinline nounwind uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { nofree nosync nounwind readnone speculatable willreturn }
attributes #2 = { noreturn nounwind "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #3 = { nounwind "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #4 = { noreturn nounwind }
attributes #5 = { nounwind }

!llvm.dbg.cu = !{!2}
!llvm.module.flags = !{!23, !24, !25, !26, !27, !28, !29}
!llvm.ident = !{!30}

!0 = !DIGlobalVariableExpression(var: !1, expr: !DIExpression())
!1 = distinct !DIGlobalVariable(name: "x", scope: !2, file: !20, line: 12, type: !21, isLocal: false, isDefinition: true)
!2 = distinct !DICompileUnit(language: DW_LANG_C99, file: !3, producer: "Debian clang version 14.0.6", isOptimized: false, runtimeVersion: 0, emissionKind: FullDebug, enums: !4, retainedTypes: !15, globals: !17, splitDebugInlining: false, nameTableKind: None)
!3 = !DIFile(filename: "/home/lasse/IdeaProjects/Dat3M/database_applications/transfer_example/transfer_example.c", directory: "/home/lasse/IdeaProjects/Dat3M", checksumkind: CSK_MD5, checksum: "b02ae31a52b723c054447594feafde1b")
!4 = !{!5}
!5 = !DICompositeType(tag: DW_TAG_enumeration_type, name: "memory_order", file: !6, line: 56, baseType: !7, size: 32, elements: !8)
!6 = !DIFile(filename: "/usr/lib/llvm-14/lib/clang/14.0.6/include/stdatomic.h", directory: "", checksumkind: CSK_MD5, checksum: "de5d66a1ef2f5448cc1919ff39db92bc")
!7 = !DIBasicType(name: "unsigned int", size: 32, encoding: DW_ATE_unsigned)
!8 = !{!9, !10, !11, !12, !13, !14}
!9 = !DIEnumerator(name: "memory_order_relaxed", value: 0)
!10 = !DIEnumerator(name: "memory_order_consume", value: 1)
!11 = !DIEnumerator(name: "memory_order_acquire", value: 2)
!12 = !DIEnumerator(name: "memory_order_release", value: 3)
!13 = !DIEnumerator(name: "memory_order_acq_rel", value: 4)
!14 = !DIEnumerator(name: "memory_order_seq_cst", value: 5)
!15 = !{!16}
!16 = !DIDerivedType(tag: DW_TAG_pointer_type, baseType: null, size: 64)
!17 = !{!0, !18}
!18 = !DIGlobalVariableExpression(var: !19, expr: !DIExpression())
!19 = distinct !DIGlobalVariable(name: "y", scope: !2, file: !20, line: 13, type: !21, isLocal: false, isDefinition: true)
!20 = !DIFile(filename: "database_applications/transfer_example/transfer_example.c", directory: "/home/lasse/IdeaProjects/Dat3M", checksumkind: CSK_MD5, checksum: "b02ae31a52b723c054447594feafde1b")
!21 = !DIDerivedType(tag: DW_TAG_volatile_type, baseType: !22)
!22 = !DIBasicType(name: "int", size: 32, encoding: DW_ATE_signed)
!23 = !{i32 7, !"Dwarf Version", i32 5}
!24 = !{i32 2, !"Debug Info Version", i32 3}
!25 = !{i32 1, !"wchar_size", i32 4}
!26 = !{i32 7, !"PIC Level", i32 2}
!27 = !{i32 7, !"PIE Level", i32 2}
!28 = !{i32 7, !"uwtable", i32 1}
!29 = !{i32 7, !"frame-pointer", i32 2}
!30 = !{!"Debian clang version 14.0.6"}
!31 = distinct !DISubprogram(name: "transaction", scope: !20, file: !20, line: 15, type: !32, scopeLine: 16, flags: DIFlagPrototyped, spFlags: DISPFlagDefinition, unit: !2, retainedNodes: !34)
!32 = !DISubroutineType(types: !33)
!33 = !{!16, !16}
!34 = !{}
!35 = !DILocalVariable(name: "vargp", arg: 1, scope: !31, file: !20, line: 15, type: !16)
!36 = !DILocation(line: 0, scope: !31)
!37 = !DILocation(line: 17, column: 5, scope: !31)
!38 = !DILocation(line: 18, column: 7, scope: !31)
!39 = !DILocation(line: 19, column: 7, scope: !31)
!40 = !DILocation(line: 20, column: 6, scope: !31)
!41 = !DILocation(line: 21, column: 1, scope: !31)
!42 = distinct !DISubprogram(name: "read", scope: !20, file: !20, line: 23, type: !32, scopeLine: 24, flags: DIFlagPrototyped, spFlags: DISPFlagDefinition, unit: !2, retainedNodes: !34)
!43 = !DILocalVariable(name: "vargp", arg: 1, scope: !42, file: !20, line: 23, type: !16)
!44 = !DILocation(line: 0, scope: !42)
!45 = !DILocation(line: 25, column: 5, scope: !42)
!46 = !DILocation(line: 26, column: 13, scope: !42)
!47 = !DILocalVariable(name: "a", scope: !42, file: !20, line: 26, type: !22)
!48 = !DILocation(line: 27, column: 13, scope: !42)
!49 = !DILocalVariable(name: "b", scope: !42, file: !20, line: 27, type: !22)
!50 = !DILocation(line: 28, column: 5, scope: !42)
!51 = !DILocation(line: 29, column: 5, scope: !52)
!52 = distinct !DILexicalBlock(scope: !53, file: !20, line: 29, column: 5)
!53 = distinct !DILexicalBlock(scope: !42, file: !20, line: 29, column: 5)
!54 = !DILocation(line: 29, column: 5, scope: !53)
!55 = !DILocation(line: 30, column: 1, scope: !42)
!56 = distinct !DISubprogram(name: "main", scope: !20, file: !20, line: 32, type: !57, scopeLine: 33, spFlags: DISPFlagDefinition, unit: !2, retainedNodes: !34)
!57 = !DISubroutineType(types: !58)
!58 = !{!22}
!59 = !DILocation(line: 36, column: 5, scope: !56)
!60 = !DILocalVariable(name: "rthread_id", scope: !56, file: !20, line: 39, type: !61)
!61 = !DIDerivedType(tag: DW_TAG_typedef, name: "pthread_t", file: !62, line: 27, baseType: !63)
!62 = !DIFile(filename: "/usr/include/x86_64-linux-gnu/bits/pthreadtypes.h", directory: "", checksumkind: CSK_MD5, checksum: "735e3bf264ff9d8f5d95898b1692fbdb")
!63 = !DIBasicType(name: "unsigned long", size: 64, encoding: DW_ATE_unsigned)
!64 = !DILocation(line: 39, column: 15, scope: !56)
!65 = !DILocation(line: 40, column: 5, scope: !56)
!66 = !DILocation(line: 46, column: 1, scope: !56)
