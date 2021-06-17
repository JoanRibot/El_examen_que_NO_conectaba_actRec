package edu.pong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceOlli {

    
    
     public ServiceOlli() {
    }

    public Usuaria cargaUsuaria(String user){

        Optional<Usuaria> usuario = Usuaria.find("nombre",user).firstResultOptional();

        if(usuario.isPresent()){
            return usuario.get();
        }
        return new Usuaria("",0);
    }

    public Item cargaItem(String item){

        Optional<Item> objeto = Item.find("nombre",item).firstResultOptional();

        if(objeto.isPresent()){
            return objeto.get();
        }
        return new Item("",0,"");
    }


    

    public List<Orden> cargaOrden(String user){

        Optional<Usuaria> usuario = Usuaria.find("nombre",user).firstResultOptional();
        if(usuario.isPresent()){
            return Orden.list("user", usuario.get());
        }

        return Arrays.asList();
        
     }

    public Orden comanda(String usuaria, String object){
        Optional<Usuaria> usuario = Usuaria.find("nombre", usuaria).firstResultOptional();
        Optional<Item> objeto = Item.find("nombre", object).firstResultOptional();

        if(usuario.isPresent() && objeto.isPresent()){
            Orden orden = new Orden(usuario.get(),objeto.get());
            if(orden.user.destreza >= orden.item.quality){
                orden.persist();
                return orden;
                }
            }
            System.out.println ("El usuario o objeto no existe");
            return null;
        }

    public List<Orden> comandaMultiple(String user, List<String> items){
        List<Orden> listaItems= new ArrayList<>();
       if(cargaUsuaria(user).nombre == ""){
           return listaItems;
        }
           
        for (String i: items){
            if(cargaItem(i).nombre != "" ){
                listaItems.add(comanda(user, i));
            }
        }                
        return listaItems;
        // List<Optional> lista = items.stream().filter(a->Item.find("nombre",a));

        // items.forEach(e->listaItems.add(comanda(user, e)));    
    }
 }
