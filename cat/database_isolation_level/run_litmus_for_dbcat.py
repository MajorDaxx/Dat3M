import os 

os.chdir(f"{os.environ['HOME']}/IdeaProjects/Dat3M")
cat_path="cat/database_isolation_level"

for cat_model in [cat_model for cat_model in os.listdir(cat_path) if os.path.isdir(f"{cat_path}/{cat_model}")]:
    
    if True:
        cat_model_path = f"{cat_path}/{cat_model}/{cat_model}.cat"
        print(cat_model_path)
        os.makedirs(f"{cat_path}/{cat_model}/litmus",exist_ok=True ) 
        os.system(f"rm {cat_path}/{cat_model}/litmus/*") 
        for litmus in filter(lambda x:x.endswith(".c"),os.listdir("database_applications")):
            litmus_name=litmus.split(".")[0]
            listmus_path = f"database_applications/{litmus}"
            print(listmus_path)
            #print(f"java -jar dartagnan/target/dartagnan.jar {cat_model_path} {listmus_path} --witness.graphviz=true  --encoding.locallyConsistent=false")
            os.system(f"java -jar dartagnan/target/dartagnan.jar {cat_model_path} {listmus_path} --witness.graphviz=true  --encoding.locallyConsistent=false")
            print(f"output/{litmus_name}-opt.png")
            if(os.path.isfile(f"output/{litmus_name}-opt.png")):
                os.system(f"cp output/{litmus_name}-opt.png {cat_path}/{cat_model}/litmus")
        os.system("rm -r output/") 

