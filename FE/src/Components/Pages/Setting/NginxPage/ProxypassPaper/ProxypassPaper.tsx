import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import { v4 as uuid } from 'uuid';
import { Locations } from 'Components/MDClass/NginxData/NginxData';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import Proxypass from '../ProxypassBox/ProxypassBox';

export default function ProxypassPaper() {
  const nginxConfig = useSettingStore((state) => state.nginxConfig);

  const [locations, setLocations] = useState<Locations[]>(
    nginxConfig.locations,
  );

  const handleProxyPassAddClick = () => {
    const tempLoacations: Locations = {
      location: '',
      url: '',
    };
    nginxConfig.locations.push(tempLoacations);
    setLocations([...nginxConfig.locations]);
  };

  const handleLocationDelClickProps = (index: number) => {
    nginxConfig.locations.splice(index, 1);
    setLocations([...nginxConfig.locations]);
  };

  return (
    <Box mt={3}>
      <Box position="relative" sx={{ top: 10, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 120,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          Proxy pass
        </Paper>
      </Box>
      <Paper sx={{ padding: 3 }}>
        <Box mb={3} sx={{ display: 'flex' }}>
          <Button
            onClick={handleProxyPassAddClick}
            variant="outlined"
            startIcon={<AddIcon />}
            sx={{ marginRight: 3 }}
          >
            Proxypass 추가
          </Button>
        </Box>
        {locations.map((value, index) => {
          return (
            <Box mb={2} key={uuid()}>
              <Proxypass
                value={value}
                index={index}
                DelClick={handleLocationDelClickProps}
                locationData={nginxConfig.locations[index]}
              />
            </Box>
          );
        })}
      </Paper>
    </Box>
  );
}
