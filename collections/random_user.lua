-- wrk.lua

-- Function to generate random strings
function getAlphaChar()
    selection = math.random(1, 3)
    if selection == 1 then return string.char(math.random(65, 90)) end
    if selection == 2 then return string.char(math.random(97, 122)) end
    return string.char(math.random(48, 57))
end

-- Function to generate random strings
function randomString(length)
           length = length or 1
                if length < 1 then return nil end
                local array = {}
                for i = 1, length do
                    array[i] = getAlphaChar()
                end
                return table.concat(array)
end

-- Function to remove trailing slashes
function removeTrailingSlash(s)
  return (s:gsub("(.-)/*$", "%1"))
end

-- Function to generate random emails
function randomEmail(domain)
   return randomString(24) .. "@" .. domain
end

request = function()
   wrk.method = "POST"
   wrk.body = "username=" .. randomString(24) ..
              "&email=" .. randomEmail("example.com") ..
              "&password=" .. randomString(12) ..
              "&strategy=PASSWORD"
   wrk.headers["Content-Type"] = "application/x-www-form-urlencoded"

   return wrk.format(nil, nil, wrk.headers, wrk.body)
end
