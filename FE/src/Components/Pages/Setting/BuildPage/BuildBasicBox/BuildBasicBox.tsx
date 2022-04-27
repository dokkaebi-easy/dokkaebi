import React, { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { Build } from 'Components/MDClass/BuildData/BuildData';
import Paper from '@mui/material/Paper';
import axios from 'axios';
import BuildProperty from '../BuildPropertyBox/BuildPropertyBox';

interface FrameworkAxios {
  frameworkTypeId: number;
  frameworkName: string;
}

interface VersionTypeAxois {
  buildTool: string[];
  frameworkVersion: string[];
}

interface buildProps {
  index: number;
  value: Build;
  DelClick: (value: number) => void;
}

export default function BuildBasicBox({ index, value, DelClick }: buildProps) {
  const [name, setName] = useState(value.name);
  const [fileDir, setFileDir] = useState(value.projectDirectory);
  const [buildPath, setBuildPath] = useState(value.buildPath);
  const [frameworkName, setFrameworkName] = useState(value.frameworkName);
  const [version, setVersion] = useState(value.version);
  const [type, setType] = useState(value.type);
  const [framAndLibs, setFramAndLibs] = useState<string[]>([
    value.frameworkName,
  ]);
  const [versions, setVersions] = useState<string[]>([value.version]);
  const [types, setTypes] = useState<string[]>([value.type]);

  const handleNameOnChange = (event: any) => {
    setName(event.target.value);
    value.name = event.target.value;
  };

  const handleFileDirOnChange = (event: any) => {
    setFileDir(event.target.value);
    value.projectDirectory = event.target.value;
  };
  const handlebuildPathOnChange = (event: any) => {
    setBuildPath(event.target.value);
    value.buildPath = event.target.value;
  };

  const handleDelOnClick = () => {
    DelClick(index);
  };

  const handlePropsFLChange = (event: string) => {
    setFrameworkName(event);
    setVersion('');
    setType('');
    value.frameworkName = event;
    value.version = '';
    value.type = '';
  };

  const handlePropsVersionChange = (event: string) => {
    setVersion(event);
    value.version = event;
  };
  const handlePropsTypeChange = (event: string) => {
    setType(event);
    value.type = event;
  };
  useEffect(() => {
    if (frameworkName && framAndLibs.length) {
      const params = { typeId: framAndLibs.indexOf(frameworkName) + 1 };
      axios
        .get('/api/project/frameworkVersion', { params })
        .then((res) => {
          const data = res.data as VersionTypeAxois;
          setVersions(data.frameworkVersion);
          setTypes(data.buildTool);
        })
        .catch((err) => {
          console.log(err);
        });
    }
    return () => {
      setVersions([value.version]);
      setTypes([value.type]);
    };
  }, [frameworkName, framAndLibs]);

  useEffect(() => {
    axios
      .get('/api/project/frameworkType')
      .then((res) => {
        const data = res.data as FrameworkAxios[];
        const arr = data.map((value) => value.frameworkName);
        setFramAndLibs(arr);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <Box>
      <Paper sx={{ padding: 3 }}>
        <Grid container spacing={2}>
          <Grid item>
            <Typography>Name</Typography>
            <TextField
              defaultValue={name}
              id="outlined-basic"
              label="Name"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Name"
              onChange={handleNameOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Framework/ Library</Typography>
            <SelectItem
              defaultValue={frameworkName}
              label="Framework/ Library"
              Items={framAndLibs}
              Change={handlePropsFLChange}
            />
          </Grid>
          <Grid item>
            <Typography>Version</Typography>
            <SelectItem
              defaultValue={version}
              label="versions"
              Items={versions}
              Change={handlePropsVersionChange}
            />
          </Grid>
          <Grid item>
            <Typography>Type</Typography>
            <SelectItem
              defaultValue={type}
              label="Types"
              Items={types}
              Change={handlePropsTypeChange}
            />
          </Grid>
          <Grid item />
          <Grid item>
            <Typography>File Dir</Typography>
            <TextField
              id="outlined-basic"
              label="Dir"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Dir"
              defaultValue={fileDir}
              onChange={handleFileDirOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Build Path</Typography>
            <TextField
              id="outlined-basic"
              label="Dir"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Dir"
              defaultValue={buildPath}
              onChange={handlebuildPathOnChange}
            />
          </Grid>
        </Grid>
        <Stack direction="row" spacing={2}>
          <Button
            variant="outlined"
            startIcon={<ArrowDropDownIcon />}
            size="small"
          >
            Property
          </Button>
          <Button
            variant="outlined"
            startIcon={<DeleteIcon />}
            size="small"
            onClick={handleDelOnClick}
          >
            Delete
          </Button>
        </Stack>
        <BuildProperty buildValue={value} />
      </Paper>
    </Box>
  );
}
