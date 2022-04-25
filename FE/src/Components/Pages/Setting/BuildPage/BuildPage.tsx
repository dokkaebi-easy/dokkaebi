import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import { v4 as uuid } from 'uuid';
import BuildBasic from 'Components/Pages/Setting/BuildPage/BuildBasic/BuildBasicBox';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import { useStore } from 'Components/Store/settingStore';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';
import NginxBasic from './NginxBasic/NginxBasic';

export default function BuildPage() {
  const project = useStore((state) => state.setProjectName);
  const buildConfig = useStore((state) => state.setBuildConfig);
  const nginxConfig = useStore((state) => state.setNginxConfig);

  const [projectName, setProjectName] = useState('');
  const [nginxData, setNginxData] = useState<Nginx>(new NginxData());
  const [buildDatas, setBuildDatas] = useState<Build[]>([new BuildData()]);

  const handlePropsDelClick = (index: number) => {
    setBuildDatas((cur) => {
      cur.splice(index, 1);
      return [...cur];
    });
  };

  const handleChange = (event: any) => {
    setProjectName(event.target.value);
  };

  const handleAddClick = () => {
    const data = new BuildData();
    setBuildDatas((cur) => [...cur, data]);
  };

  useEffect(() => {
    project(projectName);
    nginxConfig(nginxData);
    buildConfig(buildDatas);
    console.log('a');
  }, [projectName, nginxData, ...buildDatas]);

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 200 }}>
          <Typography variant="h5">Build Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3, pt: 4 }}>
          <Typography>Project Name</Typography>
          <TextField
            onChange={handleChange}
            id="outlined-basic"
            label="Project Name"
            variant="outlined"
            size="small"
            sx={{ my: 2 }}
            placeholder="ProjectName"
            defaultValue={projectName}
          />
          <Divider />
          <Box sx={{ my: 3 }}>
            {buildDatas.map((value, index) => (
              <BuildBasic
                key={uuid()}
                index={index}
                value={value}
                DelClick={handlePropsDelClick}
              />
            ))}
            <Box my={3} sx={{ display: 'flex', justifyContent: 'end' }}>
              <Button
                onClick={handleAddClick}
                variant="outlined"
                startIcon={<AddIcon />}
              >
                Add
              </Button>
            </Box>
            <Divider />
            <Box mt={3}>
              <NginxBasic nginxValue={nginxData} />
            </Box>
          </Box>
        </Paper>
      </Box>
    </Box>
  );
}
