#!/usr/bin/env ruby -wKU

require "xmlrpc/client"

# Patch XML-RPC to show us what is sent.
class XMLRPC::Client
   def call2(method, *args)
     request = create().methodCall(method, *args)
     puts request
     data = do_rpc(request, false)
     parser().parseMethodResponse(data)
   end
end

server  = XMLRPC::Client.new2("http://websearch.ramidx.com/ramxml.php")
results = server.call( "lists", "dbid"    => "dbid1139259934", "getlist" => "district" )

