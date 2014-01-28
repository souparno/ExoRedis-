package MyRedis;

public class Command
{
  static _dump_memory _mem_index=null;
  
  public String _set(String Key,String Value){
        try{  
            int flag=0;
            _dump_memory temp=_mem_index;
            for(;temp !=null;){
                if(temp.Key.equals(Key)){
                 temp.Value=Value;
                 flag=1;
                 break;
                }
                temp=temp.index;
            }
              
            if(flag==0){
              _dump_memory obj=new _dump_memory();
              obj.Key=Key;
              obj.Value=Value;
              obj.index=_mem_index;
              _mem_index=obj;
            }
     }catch(Exception e){
     return "Error : "+e;
    }
    return "Success";  
  }
  
  public String _get(String Key){
    String Value="Variable Undefined";
    _dump_memory temp=_mem_index;
    for(;temp !=null;){
        if(temp.Key.equals(Key)){
          Value=temp.Value;
          break;
        } 
        temp=temp.index;
    } 
    return Value;
  }
  
  
  public String _set_offset(String Key,int offset,int bit){
   try{   
     _dump_memory key_present= searchKey(Key);
     if(key_present !=null){
         //key_present.Key=Key;
         _offset_bit obj=search_offset_bit(key_present.bit_index,offset);
         if(obj ==null){
            _offset_bit bitobj=new _offset_bit();
            bitobj.offset_pos=offset;
            bitobj.value=bit;
            bitobj.index=key_present.bit_index;
            key_present.bit_index=bitobj;
         }
         else{
             obj.value=bit;
         }
       }
     else{ 
     
     _offset_bit bitobj=new _offset_bit();
     bitobj.offset_pos=offset;
     bitobj.value=bit;
     bitobj.index=null;
          
      _dump_memory obj=new _dump_memory();
     obj.Key=Key;
     obj.bit_index=bitobj;
     obj.index=_mem_index;
     _mem_index=obj; 
    }
   }catch(Exception e){
     return "Error : "+e;
   }
   return "Success";
  }
  
  public String _get_offset(String Key,int offset){
      _dump_memory key_present= searchKey(Key);
      if(key_present != null){
         _offset_bit off_obj= search_offset_bit(key_present.bit_index,offset);
         if(off_obj != null){
             return off_obj.value+"";
         }
         else return "Offset Undefined";
      }
      else return "Variable undefined";
  }
  
    public void _get_offset(String Key){
    _dump_memory temp=_mem_index;
    for(;temp !=null;){
        if(temp.Key.equals(Key)){
          _offset_bit offset_index=temp.bit_index;
          for(;offset_index!=null;){
              System.out.println(temp.Key+"=>"+offset_index.offset_pos+"=>"+offset_index.value);
              offset_index=offset_index.index;
            }
          break;
        } 
        temp=temp.index;
    } 
  }
  
public _offset_bit search_offset_bit(_offset_bit node,int offset){
    for(;node!=null;){
        if(node.offset_pos == offset) return node;
        node=node.index;
    }
    return null;
}    
   
  public _dump_memory searchKey(String Key){
      _dump_memory temp=_mem_index;
      for(;temp !=null;){
           if(temp.Key.equals(Key)){
                return temp;
           }
           temp=temp.index;
       }
      return null; 
    }
    
  public static void main(String args[]){
   Command obj=new Command();
   obj._set_offset("a",0,1);
   obj._set_offset("a",1,0);
   obj._set_offset("a",2,34);
   obj._set_offset("a",3,1);
   obj._set_offset("a",4,0);
   obj._set_offset("a",2,3);
   
   obj._get_offset("a");
   System.out.println(obj._get_offset("a",2));
    }
    
}

class _dump_memory{
 String Key;
 String Value;
 _dump_memory index;
 _offset_bit bit_index;
}

class _offset_bit{
 int offset_pos;
 int value;
 _offset_bit index;
}
