import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import { v4 as uuid } from 'uuid';
import DomainBox from '../DomainBox/DomainBox';

export default function DomainPaper() {
  const nginxConfig = useSettingStore((state) => state.nginxConfig);

  const [domains, setDomains] = useState<string[]>(nginxConfig.domains);

  const handleDomainAddClick = () => {
    nginxConfig.domains.push('');
    setDomains([...nginxConfig.domains]);
  };

  const handleDomainDelClickProps = (index: number) => {
    nginxConfig.domains.splice(index, 1);
    setDomains([...nginxConfig.domains]);
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
          Domain
        </Paper>
      </Box>
      <Paper sx={{ padding: 3 }}>
        <Box mb={3} sx={{ display: 'flex' }}>
          <Button
            onClick={handleDomainAddClick}
            variant="outlined"
            startIcon={<AddIcon />}
            sx={{ marginRight: 3 }}
          >
            Domain Add
          </Button>
        </Box>
        {domains.map((value, index) => {
          return (
            <DomainBox
              key={uuid()}
              value={value}
              index={index}
              domainValue={nginxConfig.domains}
              DelClick={handleDomainDelClickProps}
            />
          );
        })}
      </Paper>
    </Box>
  );
}
