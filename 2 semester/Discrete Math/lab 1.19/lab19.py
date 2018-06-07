import copy
def magic(var, count ):
    newVar=[]
    for i in var:
        if (isMagicSpecialWork(i)):
            k=copy.deepcopy(i)
            k.append(count)
            newVar.append(k)
            newVar.append([i,[count]])
        else:
            i=specialMagic(i, count)
            for z in i:
                newVar.append(z)
    return newVar
def specialMagic(var,count):
    newVar=[]
    last=copy.deepcopy(var)
    last.append([count])
    newVar.append(last)
    for i in var:
        k=copy.deepcopy(i)
        newVarForVar=[]
        for z in var:
            if(k!=z):
                newVarForVar.append(z)
            else:
                k.append(count)
                newVarForVar.append(k)
        newVar.append(newVarForVar)
    return newVar



def isMagicSpecialWork(var):
    flag=True
    if(len(var)>1):
        for i in var:
            if(isinstance(i,list)):
                flag=False
    return flag

if __name__=="__main__":
    k=int(input())
    i=1
    result = []
    result.append([1])
    z=1
    while z<=k-1:
        result=magic(result,z+1)
        print(result)
        z+=1
    k=1
    for i in result:
        print(str(k)+" "+str(i))
        k+=1
