import os
import plotly.express as px

os.chdir(f"{os.environ['HOME']}/IdeaProjects/Dat3M")
cat_path="cat/database_isolation_level"

## Visualisation
models = dict()
for cat_model in [cat_model for cat_model in os.listdir(cat_path) if os.path.isdir(f"{cat_path}/{cat_model}")]:
    litmus_tests = set(os.listdir(f"{cat_path}/{cat_model}/litmus"))
    models[cat_model] = litmus_tests
#print(models)
treemap = dict()
for item in models:
    parents=[]
    for other in models:
        if other!=item:
            len(models[item])
            intersection = len(models[item] & models[other])
            if intersection==len(models[item]) and len(models[other]) > len(models[item]):
                parents.append((other, len(models[other])))
    parents.sort(key=lambda x: x[1],reverse=False)
    treemap[item] = parents[0][0] if 0 < len(parents) else ""

names   = list(treemap.keys())
parents = [treemap[name] for name in names] 

print(names)
print(parents)
fig = px.treemap(
    names = names,
    parents = parents,
    # TODO values.
)
fig.update_traces(root_color="lightgrey")
fig.update_layout(margin = dict(t=50, l=25, r=25, b=25))
fig.show()



print(treemap)